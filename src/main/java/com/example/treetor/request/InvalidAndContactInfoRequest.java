package com.example.treetor.request;

import lombok.Data;

@Data
public class InvalidAndContactInfoRequest {
    private String email;
    private Long postId;
}
