package com.example.treetor.repository;

import com.example.treetor.entity.JobPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface TreetorRepository extends JpaRepository<JobPosts, Long> {

    @Query("SELECT a FROM JobPosts a WHERE  a.datePosted = :date")
    List<JobPosts> findByDate(LocalDate date);

    @Query("SELECT DISTINCT(j.leadsDomain) FROM JobPosts j")
    List<String> getAllJobDomains();

    @Query("SELECT j.link FROM JobPosts j")
    Set<String> findAllLinks();
}
