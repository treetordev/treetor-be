package com.example.treetor.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JobPostsUiResponse {
    private Long postId;
    private String link;
    private String postContent;
    private String leadLocation;
    private String comments;
    private LocalDate datePosted;
    private String leadsDomain;
    private boolean isInvalid;
    private boolean requestedContactInfo;
    private String notes;
}
