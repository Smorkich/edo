package com.education.service.appeal;

import model.dto.AppealDto;
import model.dto.FilePoolDto;
import org.springframework.http.ResponseEntity;

public interface AppealService {
    AppealDto save(AppealDto appealDto);

    /**
     * Метод отправки сообщения в архив
     */
    void moveToArchive(Long id);

    /**
     * Метод получения пользователя по id
     */
    AppealDto findById(Long id);

    /**
     * Метод добавления файла к обращению
     */
    AppealDto upload(Long id, FilePoolDto file);

    /**
     * Интерфейс метода для отправки запроса в edo-service на регистрацию Appeal по id
     * @param id идентификатор регистрируемого Appeal
     * @return AppealDto - DTO сущности Appeal (обращение)
     */
    AppealDto register(Long id);

    /**
     * Request for receive an appeal file in xlsx format
     *
     * @param appealId - id of appeal
     */
    ResponseEntity<byte[]> downloadAppealFile(Long appealId);

}
