package com.education.service.author;
import model.dto.AuthorDto;

import java.net.URISyntaxException;
import java.util.Collection;

public interface AuthorService {
    AuthorDto save(AuthorDto author) throws URISyntaxException;

    void delete(Long id) throws URISyntaxException;

    AuthorDto findById(Long id) throws URISyntaxException;

    Collection<AuthorDto> findAll() throws URISyntaxException;
}
