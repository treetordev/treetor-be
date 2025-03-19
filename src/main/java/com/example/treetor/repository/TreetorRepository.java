package com.example.treetor.repository;

import com.example.treetor.entity.JobPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TreetorRepository extends JpaRepository<JobPosts, Long> {


}
