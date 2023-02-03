package com.education.service.author;
import model.dto.AuthorDto;

import java.util.Collection;

public interface AuthorService {
    /**
     * Метод сохранения нового обращения в БД
     */
    AuthorDto save(AuthorDto authorDto);

    void delete(Long id);

    AuthorDto findById(Long id);

    Collection<AuthorDto> findAll();
}
