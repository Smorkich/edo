package com.education.service.email;

import model.dto.AppealDto;

public interface EmailService {
    void createEmail(AppealDto appealDto);
}
