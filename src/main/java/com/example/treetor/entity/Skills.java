package com.example.treetor.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
}
