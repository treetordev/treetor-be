package com.example.treetor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailTo;

import java.util.*;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String brevoKey;

    public static void sendTransactionalEmail() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.brevo.com/v3/smtp/email";

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept", "application/json");
        headers.set("api-key", "YOUR_API_KEY"); // Replace with your actual API key

        // Body
        Map<String, Object> emailData = new HashMap<>();

        Map<String, String> sender = new HashMap<>();
        sender.put("name", "Treetor Auto Mail");
        sender.put("email", "manish.nupt@gmail.com");

        Map<String, String> recipient = new HashMap<>();
        recipient.put("email", "manish.m1738108@gmail.com");
        recipient.put("name", "Dev Treetor");

        List<Map<String, String>> toList = new ArrayList<>();
        toList.add(recipient);

        emailData.put("sender", sender);
        emailData.put("to", toList);
        emailData.put("subject", "Hello world");
        emailData.put("htmlContent", "<html><head></head><body><p>Hello,</p>This is my first transactional email sent from Brevo.</p></body></html>");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(emailData, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        // Output response
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());
    }
}
