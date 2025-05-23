package com.example.treetor.controller;


import com.example.treetor.response.UserDetailsUiResponse;
import com.example.treetor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUserDetailsByEmail")
    public UserDetailsUiResponse getUserDetailsByEmail(@RequestParam String email){
       return userService.getUserDetailsByEmail(email);
    }
}
