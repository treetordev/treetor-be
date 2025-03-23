package com.example.treetor.service;


import com.example.treetor.entity.UserModel;
import com.example.treetor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username)  {
		// TODO Auto-generated method stub
		UserModel user = repo.findByEmail(username).orElseThrow((()-> new RuntimeException("User Not Found!!")));
		return user;
	}

	public UserDetails loadUserByUserKey(String userKey) {
		// TODO Auto-generated method stub
		UserModel user= repo.findByUserKey(userKey).orElseThrow(()->new IllegalArgumentException("wrong user key in token"));
		return user;
	}


}
