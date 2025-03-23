package com.example.treetor.service;


import com.example.treetor.entity.RefreshToken;
import com.example.treetor.entity.UserModel;
import com.example.treetor.repository.RefreshTokenRepository;
import com.example.treetor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

	@Autowired
	private RefreshTokenRepository repo;

	@Autowired
	private UserRepository userRepository;

	public int refreshTokenValidity=5*60*60*1000;

	public RefreshToken createRefreshToken(String userName)
	{

		UserModel user = userRepository.findByEmail(userName).get();
		RefreshToken refreshToken = user.getRefreshToken();
		if(refreshToken==null )
		{
			 refreshToken = RefreshToken.builder()
					.refreshToken(UUID.randomUUID().toString())
					.expiry(Instant.now().plusMillis(refreshTokenValidity))
					.user(userRepository.findByEmail(userName).get())
					.build();

		}
		else {
			refreshToken.setExpiry(Instant.now().plusMillis(refreshTokenValidity));

		}
		user.setRefreshToken(refreshToken);
		repo.save(refreshToken);
		  return refreshToken;
	}
	public RefreshToken verifyRefreshToken(String refreshToken)
	{
		RefreshToken refreshTokenobj =repo.findByRefreshToken(refreshToken).orElseThrow(()->new RuntimeException("Refresh Token does not exist"));
		if(refreshTokenobj.getExpiry().compareTo(Instant.now())<0) {
			repo.delete(refreshTokenobj);
			throw new RuntimeException("Refresh Token Expired");}

		return refreshTokenobj;
	}
}
