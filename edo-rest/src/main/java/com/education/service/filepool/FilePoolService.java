package com.education.service.filepool;

import model.dto.FilePoolDto;

public interface FilePoolService {

    /**
     * Метод сохранения нового обращения в БД
     */
    FilePoolDto save(FilePoolDto filePoolDto);
}
