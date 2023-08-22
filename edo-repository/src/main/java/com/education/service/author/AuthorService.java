package com.education.service.author;

import com.education.entity.Author;

import java.util.Collection;

public interface AuthorService {
    Author save(Author author);

    void delete(Long id);

    /**
     * Интерфейс метода, который сохраняет коллекцию Author в БД через repository
     *
     * @param authors коллекция добавляемых Author
     * @return Collection<Author> - коллекция сущности Author (авторы)
     */
    Collection<Author> saveAll(Collection<Author> authors);


    Author findById(Long id);

    Collection<Author> findAllById(Iterable<Long> ids);

    Collection<Author> findAll();
}
