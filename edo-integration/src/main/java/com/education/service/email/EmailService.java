package com.education.service.email;

import java.util.List;
import java.util.Set;

public interface EmailService {
    void createEmail(Set<String> emails, String appealURL, String appealNumber);
    void createMailWhenCreateResolution (List<String> emailsExecutors, List<String> fioExecutors,
                                         String emailSigner, String fioSigner,
                                         String emailCurator, String fioCurator,
                                         String appealURL, String appealNumber);
}
