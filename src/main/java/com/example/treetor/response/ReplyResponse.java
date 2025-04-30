package com.example.treetor.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponse {
    private boolean success;
    private List<String> replies;
    private String error;
}
