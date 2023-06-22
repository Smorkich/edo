package com.education.service.filePool.impl;

import com.education.service.filePool.FilePoolService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.constant.Constant;
import model.dto.FilePoolDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

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
    public FilePoolDto save(FilePoolDto filePoolDto) {
        String uri = URIBuilderUtil.buildURI(Constant.EDO_REPOSITORY_NAME, "api/repository/filePool").toString();
        return restTemplate.postForObject(uri, filePoolDto, FilePoolDto.class);
    }

    /**
     * Сохраняет коллекцию FilePoolDto, которая передаётся в параметр - отправляет запрос в контроллер edo-repository
     *
     * @param filePoolDtos коллекция добавляемых FilePoolDto
     * @return Collection<FilePoolDto> - коллекция DTO сущности FilePool (информация о файлах)
     */
    @Override
    public Collection<FilePoolDto> saveAll(Collection<FilePoolDto> filePoolDtos) {
        var dtos = Stream.ofNullable(filePoolDtos)
                .flatMap(Collection::stream)
                .map(this::save).toList();
        var uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL + "/all");
        HttpEntity<Collection<FilePoolDto>> httpEntity = new HttpEntity<>(dtos);
        ParameterizedTypeReference<Collection<FilePoolDto>> responseType = new ParameterizedTypeReference<>() {
        };
        return restTemplate.exchange(uri.toString(), HttpMethod.POST, httpEntity, responseType).getBody();
    }

    @Override
    public void delete(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL + "/" + id);
        restTemplate.delete(builder.toString());
    }

    @Override
    public String findById(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL + "/" + id);
        return restTemplate.getForObject(builder.toString(), String.class);
    }

    @Override
    public FilePoolDto findByUuid(UUID uuid) {
        String uri = URIBuilderUtil.buildURI(Constant.EDO_REPOSITORY_NAME,
                "api/repository/filePool/info/" + uuid).toString();
        return restTemplate.getForObject(uri, FilePoolDto.class);
    }


    public String findAll() {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/all");
        return restTemplate.getForObject(builder.toString(), String.class);
    }

    @Override
    public Collection<FilePoolDto> findAllById(String ids) {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.toString(), Collection.class);
    }

    @Override
    public void moveToArchive(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.postForObject(builder.toString(), null, String.class);
    }

    @Override
    public FilePoolDto findByIdNotArchived(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/notArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), FilePoolDto.class);
    }

    @Override
    public Collection<FilePoolDto> findAllByIdNotArchived(String ids) {
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/notArchivedAll/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.toString(), Collection.class);
    }

}
