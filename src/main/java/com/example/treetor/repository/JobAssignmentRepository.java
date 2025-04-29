package com.example.treetor.repository;

import com.example.treetor.entity.JobAssignment;
import com.example.treetor.entity.JobPosts;
import com.example.treetor.response.LeadNotesRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JobAssignmentRepository extends JpaRepository<JobAssignment, Long> {
    List<JobAssignment> findByUserEmail(String userEmail);
    List<JobAssignment> findByJobPost_DatePosted(LocalDate datePosted);
    @Query("SELECT ja.jobPost FROM JobAssignment ja WHERE ja.userEmail = :email AND ja.jobPost.datePosted = :datePosted")
    List<JobPosts> findJobPostsByEmailAndDate(@Param("email") String email, @Param("datePosted") LocalDate datePosted);

    @Modifying
    @Transactional
    @Query("UPDATE JobAssignment ja SET ja.markedInvalid = true WHERE ja.userEmail = :email AND ja.jobPost.id = :postId")
    int markInvalid(String email, Long postId);

    @Modifying
    @Transactional
    @Query("UPDATE JobAssignment ja SET ja.notes = :notes  WHERE ja.userEmail = :email AND ja.jobPost.id = :postId")
    void saveNotes(String email,Long postId,String notes);
}
