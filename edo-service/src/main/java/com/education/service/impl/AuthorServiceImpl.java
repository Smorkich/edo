package com.education.service.impl;

import com.education.service.AuthorService;
import lombok.AllArgsConstructor;
import model.dto.AuthorDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Сервис-класс с методами для реализации API
 */

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    static final String URL = "http://edo-repository/api/repository/author";
    private final RestTemplate restTemplate;

    /**
     * Сохранение Author
     */
    @Override
    public void save(AuthorDto authorDto) {
        restTemplate.postForObject(URL, authorDto, AuthorDto.class);

    }

    /**
     * Удаление Author по id
     */
    @Override
    public void delete(Long id) {
        restTemplate.delete(URL + "/" + id, AuthorDto.class);
    }

    /**
     * Поиск Author по id
     */
    @Override
    public AuthorDto findById(Long id) {
        return restTemplate.getForObject(URL + "/" + id, AuthorDto.class);
    }

    /**
     * Список Author`s
     */
    @Override
    public List<AuthorDto> findAll() {
        return restTemplate.getForObject(URL, List.class);
    }
}
