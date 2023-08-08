package com.education.service.email;

import model.dto.ResolutionDto;

/**
 * @author Kirill Vasiljev
 * Интерфейс с методом sendMessage для Resolution
 */

public interface EmailService {

    /**
     * Отправка сообщений (при создании резолюции)
     */

    void sendMessage(ResolutionDto resolutionDto);

}
