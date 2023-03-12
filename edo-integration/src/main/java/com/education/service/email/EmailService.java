package com.education.service.email;

import java.util.Set;

public interface EmailService {
    void createEmail(Set<String> emails, String appealURL, String appealNumber);
}
