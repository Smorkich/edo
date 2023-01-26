package com.education.service.author.impl;

import com.education.service.author.AuthorService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.constant.Constant;
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
    public AuthorDto save(AuthorDto authorDto) {
        String uri = URIBuilderUtil.buildURI(Constant.EDO_REPOSITORY_NAME, "api/repository/author").toString();

        return restTemplate.postForObject(uri, authorDto, AuthorDto.class);
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
