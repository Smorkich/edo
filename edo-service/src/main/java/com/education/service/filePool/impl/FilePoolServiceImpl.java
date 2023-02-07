package com.education.service.filePool.impl;

import com.education.service.filePool.FilePoolService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.constant.Constant;
import model.dto.FilePoolDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @author Nadezhda Pupina
 * Сервис реализует методы jpa repository и обычные методы
 */
@Service
@AllArgsConstructor
public class FilePoolServiceImpl implements FilePoolService {

    static final String URL = "http://edo-repository/api/repository/filePool";
    private final RestTemplate restTemplate;

    @Override
    public FilePoolDto save(FilePoolDto filePoolDto) {
        String uri = URIBuilderUtil.buildURI(Constant.EDO_REPOSITORY_NAME, "api/repository/filePool").toString();

        return restTemplate.postForObject(uri, filePoolDto, FilePoolDto.class);
    }

    @Override
    public void delete(Long id) {
        restTemplate.delete(URL + "/" + id, FilePoolDto.class);
    }

    @Override
    public String findById(Long id) {
        return restTemplate.getForObject(URL + "/" + id, String.class);
    }

    @Override
    public FilePoolDto findByUuid(UUID uuid) {
        String uri = URIBuilderUtil.buildURI(Constant.EDO_REPOSITORY_NAME, "api/repository/filePool/info/" + uuid).toString();
        return restTemplate.getForObject(uri, FilePoolDto.class);
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
        return restTemplate.getForObject(URL + "/notArchived/" + id, FilePoolDto.class);
    }

    @Override
    public Collection<FilePoolDto> findAllByIdNotArchived(String ids) {
        return restTemplate.getForObject(URL + "/notArchivedAll/" + ids, List.class);
    }

}
