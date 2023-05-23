package com.example.bezbednostbackend.service;

import jakarta.mail.MessagingException;
import org.springframework.core.io.FileSystemResource;

public interface EmailService {

    void sendSimpleEmail(String to, String subject, String body);
    void sendMIMEEmail(String to, String subject, String htmlContent) throws MessagingException;
    void sendEmailWithAttachment(String to, String subject, String body, String pathToAttachment) throws MessagingException;
}
