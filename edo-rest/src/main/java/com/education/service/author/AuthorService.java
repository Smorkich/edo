package com.education.service.author;

import model.dto.AuthorDto;

public interface AuthorService {

    /**
     * Метод сохранения нового обращения в БД
     */
    AuthorDto save(AuthorDto authorDto);
}
