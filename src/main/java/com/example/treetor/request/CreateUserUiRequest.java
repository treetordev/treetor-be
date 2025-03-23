package com.example.treetor.request;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CreateUserUiRequest {
	private String name;
	private String email;
	private String password;
	private String about;
	private String role;

}
