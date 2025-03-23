package com.example.treetor.request;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Data
public class CreateUserUiRequest {
	private String name;
	private String email;
	private String password;
	private Set<String> skills;
	private String role;
}
