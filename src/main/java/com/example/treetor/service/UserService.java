package com.example.treetor.service;

import com.example.treetor.entity.UserModel;
import com.example.treetor.repository.UserRepository;
import com.example.treetor.response.UserDetailsUiResponse;
import com.example.treetor.utility.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


	@Autowired
	UserRepository repo;

	@Autowired
	PasswordEncoder encoder;

	public UserService() {

	}

	public List<UserModel> getUsers()
	{
		return repo.findAll();
	}

	public UserModel createUser(UserModel user)
	{
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}
	
	public static String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }

	public UserDetailsUiResponse getUserDetailsByEmail(String email) {
		UserModel user = repo.findByEmail(email).orElseThrow((()-> new RuntimeException("User Not Found!!")));
		return CommonHelper.convertToUserDetailsUiResponse(user);
	}
}
