package com.example.treetor.repository;

import com.example.treetor.entity.JobPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TreetorRepository extends JpaRepository<JobPosts, Long> {

    @Query("SELECT a FROM JobPosts a WHERE  a.datePosted = :date")
    List<JobPosts> findByDate(LocalDate date);
}
