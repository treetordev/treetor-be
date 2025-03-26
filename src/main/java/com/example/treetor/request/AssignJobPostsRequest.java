package com.example.treetor.request;

import java.util.List;

public class AssignJobPostsRequest {
    private String userEmail;
    private List<Long> postIds; // Only send post IDs since JobPosts exist in DB

    // Getters and Setters

    public List<Long> getPostIds() {
        return postIds;
    }

    public void setPostIds(List<Long> postIds) {
        this.postIds = postIds;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
