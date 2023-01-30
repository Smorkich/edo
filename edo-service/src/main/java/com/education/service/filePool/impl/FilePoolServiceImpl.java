package com.education.service.filePool.impl;

import com.education.service.filePool.FilePoolService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FilePoolDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

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
    public FilePoolDto save(FilePoolDto filePoolDto) {
       return restTemplate.postForObject(URL, filePoolDto, FilePoolDto.class);
    }

    @Override
    public void delete(Long id) {
        restTemplate.delete(URL + "/" + id, FilePoolDto.class);
    }

    @Override
    public String findById(Long id) {
        return restTemplate.getForObject(URL + "/" + id, String.class);
    }

    public String findAll() {
        return restTemplate.getForObject(URL + "/all", String.class);
    }

    @Override
    public Collection<FilePoolDto> findAllById(String ids) {
        return restTemplate.getForObject(URL + "/all/" + ids, List.class);
    }

    @Override
    public void moveToArchive(Long id) {
        restTemplate.postForObject(URL + "/" + id, null, FilePoolDto.class);
    }

    @Override
    public FilePoolDto findByIdNotArchived(Long id) {
        return restTemplate.getForObject(URL + "/notArchived/" + id,FilePoolDto.class);
    }

    @Override
    public Collection<FilePoolDto> findAllByIdNotArchived(String ids) {
        return restTemplate.getForObject(URL + "/notArchivedAll/" + ids, List.class);
    }

}
