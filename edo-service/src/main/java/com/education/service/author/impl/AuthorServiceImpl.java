package com.education.service.author.impl;

import com.education.service.author.AuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AuthorDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Сервис-класс с методами для реализации API
 */

@Service
@AllArgsConstructor
@Log4j2
public class AuthorServiceImpl implements AuthorService {

    static final String URL = "http://edo-repository/api/repository/author";
    private final RestTemplate restTemplate;

    /**
     * Сохранение Author
     */
    @Override
    public AuthorDto save(AuthorDto authorDto) {
        try {
            return restTemplate.postForObject(URL, authorDto, AuthorDto.class);
        } catch (Exception e) {
            log.warn("Saving author {}, failed!", authorDto);
            throw e;
        }
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
