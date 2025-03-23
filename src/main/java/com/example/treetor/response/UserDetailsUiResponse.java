package com.example.treetor.response;

import lombok.Data;

@Data
public class UserDetailsUiResponse {

    private String about;
    private String name;
    private String userKey;
    private String roles;
    private String email;
    private String userDetailsId;

}