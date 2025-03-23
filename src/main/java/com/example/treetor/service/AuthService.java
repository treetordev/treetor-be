package com.example.treetor.service;


import com.example.treetor.entity.UserModel;
import com.example.treetor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

	@Autowired
	private UserDetailsService userDetailsService;



	@Autowired
	com.example.treetor.repository.UserRepository repo;

	public UserDetails loadUserByUsername(String email) {
		// TODO Auto-generated method stub
		return userDetailsService.loadUserByUsername(email);

	}

	@Transactional
	public String forgotPassword(UserModel model) {
		// TODO Auto-generated method stub
		if(repo.findByEmail(model.getEmail()).isEmpty())
		{
			return "No user found with the given email Id";
		}
		else
		{
		 try {
			 repo.changePassword(model.getEmail(), model.getPassword());
			 return "Password changed successfully";
		 }
		 catch(Exception ex)
		 {
			 System.out.print(ex.getStackTrace());
			 return "Error occured while changing the password";
		 }
		}

	}

}