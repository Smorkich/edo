package com.education.service.impl;

import com.education.service.FilePoolServiceInt;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import model.dto.FilePoolDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class FilePoolService implements FilePoolServiceInt {

    static final String URL = "http://edo-repository/api/repository/author";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final Logger logger = Logger.getLogger(FilePoolService.class.getName());
    @Override
    public void save(FilePoolDto filePoolDto) {
        logger.info("sent a request to save the department in edo - repository");
        restTemplate.postForObject(URL, filePoolDto, FilePoolDto.class);
        logger.info("sent a request to save the department in edo - repository");

    }

    @Override
    public void delete(Long id) {
        restTemplate.delete(URL+"/"+id,FilePoolDto.class);
    }

    @Override
    public FilePoolDto findById(Long id) {
        return restTemplate.getForObject(URL+"/"+id,FilePoolDto.class);
    }

    @Override
    public Collection<FilePoolDto> findAllById(Iterable<Long> ids) {
        return null;
    }


    @Override
    public List<FilePoolDto> findAll() {
        return restTemplate.getForObject(URL,List.class);
    }

    @Override
    public void moveToArchive(Long id) {

    }

    @Override
    public FilePoolDto findByIdNotArchived(Long id) {
        return null;
    }

    @Override
    public Collection<FilePoolDto> findAllByIdNotArchived(Iterable<Long> ids) {
        return null;
    }

}
