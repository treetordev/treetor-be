package com.example.treetor.controller;

import com.example.treetor.entity.JobPosts;
import com.example.treetor.request.AssignJobPostsRequest;
import com.example.treetor.request.InvalidAndContactInfoRequest;
import com.example.treetor.response.LeadNotesRequest;
import com.example.treetor.service.TreetorService;
import com.example.treetor.service.UserService;
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

    @Autowired
    UserService userService;

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

    @PostMapping("/assign")
    public ResponseEntity<String> assignJobPosts(@RequestBody AssignJobPostsRequest request) {
        userService.assignJobPostsToUser(request);
        return ResponseEntity.ok("Job posts assigned successfully");
    }

    @GetMapping("getAssignedPostByEmail")
    public ResponseEntity<List<JobPosts>> getAssignedPostByEmail(@RequestParam LocalDate date,
                                                                 @RequestParam String email){
        List<JobPosts> posts= treetorService.getAssignedPostByEmail(date,email);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/markInvalid")
    public ResponseEntity<String> markInvalid(@RequestBody InvalidAndContactInfoRequest request) {
        userService.markInvalid(request);
        return ResponseEntity.ok("Job post marked invalid");
    }



    @PostMapping("/saveLeadNotes")
    public ResponseEntity<String> saveLeadNotes(@RequestBody LeadNotesRequest request) {
        userService.saveLeadNotes(request);
        return ResponseEntity.ok("Job post marked invalid");
    }
    @PostMapping("/getLeadNotes")
    public ResponseEntity<String> getLeadNotes(@RequestParam String email, @RequestParam Long postId) {
        String resp= userService.getLeadNotes(email,postId);
        return ResponseEntity.ok(resp);
    }
}



