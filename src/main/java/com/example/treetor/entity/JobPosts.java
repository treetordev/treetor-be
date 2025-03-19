package com.example.treetor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPosts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String link;
    private String postContent;
    private String leadLocation;
    private String comments;
    private LocalDate datePosted;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getLeadLocation() {
        return leadLocation;
    }

    public void setLeadLocation(String leadLocation) {
        this.leadLocation = leadLocation;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }
}
