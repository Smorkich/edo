package com.education.service.filepool.impl;

import com.education.service.filepool.FilePoolService;
import lombok.AllArgsConstructor;
import model.dto.FilePoolDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class FilePoolServiceImpl implements FilePoolService {
    private final RestTemplate restTemplate;
    private static final String URL = "http://edo-repository/api/repository/filePool";

    @Override
    public FilePoolDto save(FilePoolDto filePoolDto) {
        return restTemplate.postForObject(URL, filePoolDto, FilePoolDto.class);
    }
}
