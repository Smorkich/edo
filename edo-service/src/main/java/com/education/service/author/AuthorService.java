package com.education.service.author;

import model.dto.AuthorDto;

import java.util.Collection;

public interface AuthorService {
    AuthorDto save(AuthorDto author);

    /**
     * Интерфейс для метода, который сохраняет коллекцию сущностей AuthorDto
     *
     * @param authorDtos - коллекция сохраняемых dto
     * @return Collection<FilePoolDto> - коллекция сохранённых dto
     */
    Collection<AuthorDto> saveAll(Collection<AuthorDto> authorDtos);

    void delete(Long id);

    AuthorDto findById(Long id);

    Collection<AuthorDto> findAll();
}
