package com.education.service.filePool.impl;

import com.education.service.filePool.FilePoolService;
import com.education.util.URIBuilderUtil;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import model.constant.Constant;
import model.dto.FilePoolDto;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;

/**
 * @author Nadezhda Pupina
 * Сервис реализует методы jpa repository и обычные методы
 */
@Service
@AllArgsConstructor
public class FilePoolServiceImpl implements FilePoolService {

    private final RestTemplate restTemplate;


    @Override
    public FilePoolDto save(FilePoolDto filePoolDto) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/");
        return restTemplate.postForObject(builder.build(), filePoolDto, FilePoolDto.class);
    }

    @Override
    public void delete(Long id) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.delete(builder.build());
    }

    @Override
    public String findById(Long id) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), String.class);
    }

    public String findAll() throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/all");
        return restTemplate.getForObject(builder.build(), String.class);
    }

    @Override
    public Collection<FilePoolDto> findAllById(String ids) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), Collection.class);
    }

    @Override
    public void moveToArchive(Long id) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.postForObject(builder.build(), null, String.class);
    }

    @Override
    public FilePoolDto findByIdNotArchived(Long id) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/notArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), FilePoolDto.class);
    }

    @Override
    public Collection<FilePoolDto> findAllByIdNotArchived(String ids) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/notArchivedAll/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), Collection.class);
    }

}
