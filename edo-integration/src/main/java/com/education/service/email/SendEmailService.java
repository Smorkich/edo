package com.education.service.email;

public interface SendEmailService {
    void sendEmail(String toAddress, String subject, String message);

    void sendDeadlineEmail(String email, String message);
}
