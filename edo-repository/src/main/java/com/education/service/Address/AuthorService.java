package com.education.service.Address;

import com.education.entity.Author;
import org.springframework.stereotype.Component;

import java.util.List;
public interface AuthorService {
    void save(Author author);

    void delete(Long id);

    Author findById(Long id);

    List<Author> findAllById(Iterable<Long> ids);

    List<Author> findAll();
}
