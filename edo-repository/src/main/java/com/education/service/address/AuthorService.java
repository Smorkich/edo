package com.education.service.address;

import com.education.entity.Author;

import java.util.List;
public interface AuthorService {
    void save(Author author);

    void delete(Long id);

    Author findById(Long id);

    List<Author> findAllById(Iterable<Long> ids);

    List<Author> findAll();
}
