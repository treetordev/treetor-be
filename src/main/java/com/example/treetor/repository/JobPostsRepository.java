package com.example.treetor.repository;

import com.example.treetor.entity.JobPosts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostsRepository extends JpaRepository<JobPosts,Long> {
}
