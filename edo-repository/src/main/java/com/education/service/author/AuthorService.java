package com.education.service.author;

import com.education.entity.Author;

import java.net.URISyntaxException;
import java.util.Collection;

public interface AuthorService {
    Author save(Author author) throws URISyntaxException;

    void delete(Long id) throws URISyntaxException;

    Author findById(Long id) throws URISyntaxException;

//    Collection<Author> findAllById(Iterable<Long> ids);

    Collection<Author> findAll() throws URISyntaxException;
}
