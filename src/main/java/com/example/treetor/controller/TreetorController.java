package com.example.treetor.controller;

import com.example.treetor.entity.JobPosts;
import com.example.treetor.service.TreetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/treetot")
public class TreetorController {

    @Autowired
    TreetorService treetorService;

    @PostMapping("/upload")
    public ResponseEntity<List<JobPosts>> uploadExcel(@RequestParam("file") MultipartFile file, @RequestParam LocalDate date) {
        List<JobPosts> jobPosts = treetorService.parseExcelFile(file,date);
        return ResponseEntity.ok(jobPosts);
    }

    @GetMapping("/getAllLeadsByDate")
    public ResponseEntity<List<JobPosts>> getAllLeadsByDate(@RequestParam LocalDate date){
       List<JobPosts> posts= treetorService.getAllTodaysPost(date);
       return ResponseEntity.ok(posts);
    }

    @GetMapping("/getAllJobDomains")
    public List<String> getAllJobDomains(){
        return treetorService.getAllJobDomains();
    }

    @GetMapping("/getAllUsers")
    public List<String> getAllUsers(){
        return treetorService.getAllUsers();
    }
}
