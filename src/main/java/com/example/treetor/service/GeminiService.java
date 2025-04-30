package com.example.treetor.service;

import com.example.treetor.config.GeminiConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GeminiService {

    @Autowired
    private GeminiConfig geminiConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    //@Cacheable(value = "replies", key = "#postContent.hashCode()")
    public List<String> generateReplies(String postContent) {
        try {
            // Create request payload
            String prompt = buildPrompt(postContent);
            GeminiRequest request = new GeminiRequest(Collections.singletonList(
                    new GeminiContent(Collections.singletonList(
                            new GeminiPart("text", prompt)
                    ))
            ));

            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-goog-api-key", geminiConfig.getApiKey());

            // Make the API call
            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(request), headers);
            String apiUrl = geminiConfig.getApiUrl();
            String response = restTemplate.postForObject(apiUrl, entity, String.class);

            // Parse response
            return processResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonList("Error generating replies: " + e.getMessage());
        }
    }

    private String buildPrompt(String postContent) {
        return "You are a professional networking assistant helping to craft thoughtful LinkedIn replies.\n\n" +
                "LinkedIn post content: \"" + postContent + "\"\n\n" +
                "Generate 3 distinct, personalized replies to this LinkedIn post that are:\n" +
                "1. Professional and relevant to the post topic\n" +
                "2. Engaging and likely to start a conversation\n" +
                "3. Not overly salesy but subtly positioning the responder as knowledgeable\n\n" +
                "Format the 3 replies separately, each around 2-3 sentences. Label them as Reply 1:, Reply 2:, and Reply 3:";
    }

    private List<String> processResponse(String response) {
        try {
            // Parse JSON response
            Map<String, Object> jsonResponse = objectMapper.readValue(response, Map.class);

            // Extract text content from the response structure
            String content = extractTextFromResponse(jsonResponse);

            // Use regex to extract the 3 replies
            List<String> replies = new ArrayList<>();
            Pattern pattern = Pattern.compile("Reply \\d+:\\s*(.+?)(?=Reply \\d+:|$)", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(content);

            while (matcher.find() && replies.size() < 3) {
                replies.add(matcher.group(1).trim());
            }

            // Fallback if pattern matching fails
            if (replies.isEmpty()) {
                // Just split by newlines and try to extract meaningful content
                String[] lines = content.split("\n\n");
                for (String line : lines) {
                    if (!line.trim().isEmpty() && replies.size() < 3) {
                        replies.add(line.trim());
                    }
                }
            }

            return replies;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonList("Error processing response: " + e.getMessage());
        }
    }

    private String extractTextFromResponse(Map<String, Object> jsonResponse) {
        try {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) jsonResponse.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> candidate = candidates.get(0);
                Map<String, Object> content = (Map<String, Object>) candidate.get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                if (parts != null && !parts.isEmpty()) {
                    return (String) parts.get(0).get("text");
                }
            }
            return "No text content found in response";
        } catch (Exception e) {
            return "Error extracting text: " + e.getMessage();
        }
    }

    // Request classes for Gemini API
    @Data
    static class GeminiRequest {
        private List<GeminiContent> contents;

        public GeminiRequest(List<GeminiContent> contents) {
            this.contents = contents;
        }
    }

    @Data
    static class GeminiContent {
        private List<GeminiPart> parts;

        public GeminiContent(List<GeminiPart> parts) {
            this.parts = parts;
        }
    }

    @Data
    static class GeminiPart {
        private String text;

        public GeminiPart(String type, String text) {
            this.text = text;
        }
    }
}
