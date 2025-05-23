package com.example.treetor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_assignments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private boolean markedInvalid;
    private boolean contactInfoRequested;
    @Column(columnDefinition = "TEXT")
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }



    public boolean isMarkedInvalid() {
        return markedInvalid;
    }

    public void setMarkedInvalid(boolean markedInvalid) {
        this.markedInvalid = markedInvalid;
    }

    public boolean isContactInfoRequested() {
        return contactInfoRequested;
    }

    public void setContactInfoRequested(boolean contactInfoRequested) {
        this.contactInfoRequested = contactInfoRequested;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public JobPosts getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPosts jobPost) {
        this.jobPost = jobPost;
    }

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private JobPosts jobPost;

    public JobAssignment(String userEmail, JobPosts jobPost) {
        this.userEmail = userEmail;
        this.jobPost = jobPost;
    }

    // Getters and Setters
}

