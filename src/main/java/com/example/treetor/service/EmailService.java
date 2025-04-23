package com.example.treetor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailTo;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {

    private final TransactionalEmailsApi apiInstance;

    public EmailService(@Value("${brevo.api.key}") String apiKey) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        // Configure API key authorization
        ApiKeyAuth apiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKeyAuth.setApiKey(apiKey);

        apiInstance = new TransactionalEmailsApi();
    }

    public void sendEmail(String toEmail, String toName, String subject, String htmlContent, String textContent) {
        SendSmtpEmail email = new SendSmtpEmail();

        // Set recipient(s)
        SendSmtpEmailTo recipient = new SendSmtpEmailTo();
        recipient.setEmail(toEmail);
        recipient.setName(toName);
        email.setTo(Arrays.asList(recipient));

        // Set email subject
        email.setSubject(subject);

        // Set email content - both HTML and plain text versions
        email.setHtmlContent(htmlContent);
        email.setTextContent(textContent);

        try {
            // Send the email
            CreateSmtpEmail response = apiInstance.sendTransacEmail(email);
            System.out.println("Email sent successfully with ID: " + response.getMessageId());
        } catch (ApiException e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
