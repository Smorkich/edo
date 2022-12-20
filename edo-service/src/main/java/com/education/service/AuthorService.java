package com.education.service;
import model.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    void save(AuthorDto author);

    void delete(Long id);

    AuthorDto findById(Long id);

    List<AuthorDto> findAll();
}
