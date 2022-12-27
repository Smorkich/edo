package com.education.service.address.impl;

import com.education.service.address.AppealService;
import lombok.AllArgsConstructor;
import model.dto.AppealDto;
import model.dto.AuthorDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Service в "edo-service", служит для связи контроллера и RestTemplate
 */
@Service
@AllArgsConstructor
public class AppealServiceImpl implements AppealService {

    /**
     * Константа "URL" хранит URL по которому мы делаем запрос при помощи RestTemplate
     */
    private final String URL = "http://edo-repository/api/repository/appeal";

    /**
     * Поле "restTemplate" нужно для вызова RestTemplate,
     * который нужен для совершения CRUD-операции по заданному URL
     */
    private final RestTemplate restTemplate;

    /**
     * Метод сохранения нового адреса в БД
     */
    public void save(AppealDto appealDto) {
        Collection<AuthorDto> authorDtoCollection= new ArrayList<>();
        appealDto.getAuthorsDto().forEach(authorDto -> {
            restTemplate
                    .postForObject("http://edo-repository/api/repository/author",authorDto, AuthorDto.class);

            System.out.println(findAll().stream().mapToLong(AuthorDto::getId));

        });

    }


    /**
     * Метод, который возвращает адрес по Id
     */
    public String findById(long id) {
        return restTemplate.getForObject(URL + "/" + id, String.class);
    }




    /**
     * Метод, который возвращает все адреса//////////////////////////
     */
    public Collection<AuthorDto> findAll() {
        return restTemplate.getForObject("http://edo-repository/api/repository/author", List.class);
    }

    /**
     * Метод удаления адреса из БД
     */
    public void delete(long id) {
        restTemplate.delete(URL + "/" + id, AppealDto.class);
    }

}
