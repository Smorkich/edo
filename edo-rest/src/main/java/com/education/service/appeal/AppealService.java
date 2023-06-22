package com.education.service.appeal;

import model.dto.AppealDto;
import model.dto.FilePoolDto;
import org.springframework.web.multipart.MultipartFile;

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

}
