package com.education.service.email;

import java.util.List;

public interface EmailService {
    void createEmail(List<String> emails, String appealURL, String appealNumber);
    void createMailWhenCreateResolution (List<String> emailsExecutors, List<String> fioExecutors,
                                         String emailSigner, String fioSigner,
                                         String emailCurator, String fioCurator,
                                         String appealURL, String appealNumber);
}
