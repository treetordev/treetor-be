package com.example.treetor.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
	private String token;
	private String userName;
	//private String refreshToken;
}
