package com.education.service.filePool.impl;

import com.education.service.filePool.FilePoolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FilePoolDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Nadezhda Pupina
 * Сервис реализует методы jpa repository и обычные методы
 */
@Service
@Log4j2
@AllArgsConstructor
public class FilePoolServiceImpl implements FilePoolService {

    static final String URL = "http://edo-repository/api/repository/filePool";
    private final RestTemplate restTemplate;


    @Override
    public void save(FilePoolDto filePoolDto) {
        log.info("sent a request to save the department in edo - repository");
        restTemplate.postForObject(URL, filePoolDto, FilePoolDto.class);
        log.info("sent a request to save the department in edo - repository");

    }

    @Override
    public void delete(Long id) {
        log.info("sent a request to delete the department in edo - repository");
        restTemplate.delete(URL + "/" + id, FilePoolDto.class);
    }

    @Override
    public String findById(Long id) {
        log.info("sent a request to findById the department in edo - repository");
        return restTemplate.getForObject(URL + "/" + id, String.class);
    }

    public String findAll() {
        return restTemplate.getForObject(URL + "/all", String.class);
    }

    @Override
    public Collection<FilePoolDto> findAllById(String ids) {
        log.info("sent a request to findAllById the department in edo - repository");
        return restTemplate.getForObject(URL + "/all/" + ids, List.class);
    }


    @Override
    public void moveToArchive(Long id) {
        log.info("sent a request to moveToArchive the department in edo - repository");
        restTemplate.postForObject(URL + "/" + id, null, FilePoolDto.class);
    }

    @Override
    public FilePoolDto findByIdNotArchived(Long id) {
        log.info("sent a request to findByIdNotArchived the department in edo - repository");
        return restTemplate.getForObject(URL + "/NotArchived/" + id,FilePoolDto.class);
    }

    @Override
    public Collection<FilePoolDto> findAllByIdNotArchived(String ids) {
        log.info("sent a request to findAllByIdNotArchived the department in edo - repository");
        return restTemplate.getForObject(URL + "/NotArchivedAll/" + ids, List.class);
    }

}
