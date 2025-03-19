package com.example.treetor;

import com.example.treetor.entity.JobPosts;
import com.example.treetor.service.TreetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/treetot")
public class TreetorController {

    @Autowired
    TreetorService treetorService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        List<JobPosts> jobPosts = treetorService.parseExcelFile(file);
        treetorService.saveJobPosts(jobPosts);
        return ResponseEntity.ok("done");
    }
}
