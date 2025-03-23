package com.example.treetor.controller;

import com.example.treetor.entity.RefreshToken;
import com.example.treetor.request.CreateUserUiRequest;
import com.example.treetor.request.JwtRequest;
import com.example.treetor.request.RefreshTokenRequest;
import com.example.treetor.response.JwtResponse;
import com.example.treetor.service.AuthBl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthBl authBl;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
		request.setEmail(request.getEmail().toLowerCase());
		authBl.doAuthenticate(request.getEmail(), request.getPassword());

		UserDetails userDetails = authBl.loadUserByUsername(request.getEmail());
		String token = authBl.generateToken(userDetails);
		RefreshToken createRefreshToken = authBl.createRefreshToken(request.getEmail());

		JwtResponse response = JwtResponse.builder().token(token)
				.userName(userDetails.getUsername())
				.build();
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);


		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/forgotPassword")
	public String forgotPassword(@RequestBody JwtRequest request)
	{
		return authBl.forgotPassword(request);
	}
	
	@PostMapping("/createUser")
	public Boolean createUser(@RequestBody CreateUserUiRequest req)
	{
		return authBl.createUser(req);
	}
	
	@PostMapping("/refresh")
	public JwtResponse refreshJwtToken(@RequestBody RefreshTokenRequest request){
		
		return null;
	}



}

