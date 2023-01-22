package com.education.service.author.impl;

import com.education.service.author.AuthorService;
import lombok.AllArgsConstructor;
import model.dto.AuthorDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final RestTemplate restTemplate;
    private static final String URL = "http://edo-repository/api/repository/author";

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return  restTemplate.exchange(URL, HttpMethod.POST, new HttpEntity<>(authorDto, headers), AuthorDto.class).getBody();
    }
}
