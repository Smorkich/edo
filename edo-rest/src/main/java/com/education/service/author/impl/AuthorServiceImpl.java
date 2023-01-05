package com.education.service.author.impl;

import com.education.service.author.AuthorService;
import lombok.AllArgsConstructor;
import model.dto.AuthorDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final RestTemplate restTemplate;

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        return  restTemplate.postForObject("http://edo-repository/api/repository/author", authorDto, AuthorDto.class);
    }
}
