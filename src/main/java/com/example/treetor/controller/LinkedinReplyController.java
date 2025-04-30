package com.example.treetor.controller;

import com.example.treetor.request.PostContentRequest;
import com.example.treetor.response.ReplyResponse;
import com.example.treetor.service.GeminiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/linkedin")
public class LinkedinReplyController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/generate-replies")
    public ResponseEntity<ReplyResponse> generateReplies(@Valid @RequestBody PostContentRequest request) {
        try {
            // Validate input
            if (request.getPostContent() == null || request.getPostContent().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ReplyResponse(false, null, "Post content cannot be empty"));
            }

            // Generate replies using the Gemini service
            List<String> replies = geminiService.generateReplies(request.getPostContent());

            return ResponseEntity.ok(new ReplyResponse(true, replies, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new ReplyResponse(false, null, "Error generating replies: " + e.getMessage()));
        }
    }

}
