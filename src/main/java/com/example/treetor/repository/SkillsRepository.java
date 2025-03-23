package com.example.treetor.repository;

import com.example.treetor.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillsRepository extends JpaRepository<Skills, Long> {
    Optional<Skills> findByName(String name); // Find a skill by its name
}
