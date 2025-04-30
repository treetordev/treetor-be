package com.example.treetor.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadNotesRequest {
    private String email;
    private Long postId;
    private String notes;
}
