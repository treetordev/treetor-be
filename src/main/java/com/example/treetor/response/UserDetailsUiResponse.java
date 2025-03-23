package com.example.treetor.response;

import lombok.Data;

import java.util.Set;

@Data
public class UserDetailsUiResponse {

    private Set<String> skills;
    private String name;
    private String userKey;
    private String roles;
    private String email;
    private String userDetailsId;

}