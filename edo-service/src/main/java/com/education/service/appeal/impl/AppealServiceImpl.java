package com.education.service.appeal.impl;

import com.education.service.appeal.AppealService;
import com.education.service.author.AuthorService;
import com.education.service.filePool.FilePoolService;
import com.education.service.question.QuestionService;
import com.education.util.URIBuilderUtil;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import model.constant.Constant;
import model.dto.AppealDto;
import model.dto.AuthorDto;
import model.dto.FilePoolDto;
import model.dto.QuestionDto;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static model.constant.Constant.APPEAL_URL;
import static model.enum_.Status.NEW_STATUS;

/**
 * Сервис-слой для Appeal
 */
@Service
@AllArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;
    private final String URL = "http://edo-repository/api/repository/appeal";

    private final AuthorService authorService;
    private final QuestionService questionService;
    private final FilePoolService filePoolService;

    /**
     * Нахождение обращения по id
     */
    @Override
    public AppealDto findById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(APPEAL_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), AppealDto.class);
    }

    /**
     * Нахождение всех обращений
     */
    @Override
    public Collection<AppealDto> findAll() {
        return restTemplate.getForObject(URL, Collection.class);
    }

    /**
     * Сохранение обращения
     */
    @Override
    public AppealDto save(AppealDto appealDto) throws URISyntaxException {
        // Назначения статуса и времени создания
        appealDto.setAppealsStatus(NEW_STATUS);
        appealDto.setCreationDate(ZonedDateTime.now());

        // Списки, которые хранят, новые сущности
        List<AuthorDto> savedAuthors = new ArrayList<>();
        List<QuestionDto> savedQuestions = new ArrayList<>();
        List<FilePoolDto> savedFiles = new ArrayList<>();

        try {
            // Сохранение новых авторов
            appealDto.setAuthors(appealDto.getAuthors().stream()
                    .map(authorDto -> {
                        if (authorDto.getId() == null) {
                            try {
                                authorDto = authorService.save(authorDto);
                            } catch (URISyntaxException e) {
                                throw new RuntimeException(e);
                            }
                            savedAuthors.add(authorDto);
                        }
                        return authorDto;
                    })
                    .collect(Collectors.toList()));

            // Сохранение новых вопросов
            appealDto.setQuestions(appealDto.getQuestions().stream()
                    .map(questionDto -> {
                        if (questionDto.getId() == null) {
                            questionDto = questionService.save(questionDto);
                            savedQuestions.add(questionDto);
                        }
                        return questionDto;
                    })
                    .collect(Collectors.toList()));

            // Сохранение новых файлов
            appealDto.setFile(appealDto.getFile().stream()
                    .map(filePoolDto -> {
                        if (filePoolDto.getId() == null) {
                            filePoolDto = filePoolService.save(filePoolDto);
                            savedFiles.add(filePoolDto);
                        }
                        return filePoolDto;
                    })
                    .collect(Collectors.toList()));


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            var instances = eurekaClient.getApplication("edo-repository").getInstances();
            var instance = instances.get(new Random().nextInt(instances.size()));
            var builder = new URIBuilder();
            builder.setHost(instance.getHostName())
                    .setPort(instance.getPort())
                    .setPath(APPEAL_URL)
                    .setPath("/");

            return restTemplate.getForObject(builder.build(), AppealDto.class);
        } catch (Exception e) {

            // Удаление сохранённых вложенных сущностей
            savedAuthors.forEach(authorDto -> authorService.delete(authorDto.getId()));
            savedFiles.forEach(filePoolDto -> filePoolService.delete(filePoolDto.getId()));
            savedQuestions.forEach(questionDto -> questionService.delete(questionDto.getId()));

            throw e;
        }
    }

    /**
     * Удаления обращения по Id
     */
    @Override
    public void delete(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(APPEAL_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.delete(builder.build());
    }

    /**
     * Перенос обращения в архив по id
     */
    @Override
    public void moveToArchive(Long id) throws URISyntaxException {
        AppealDto appeal = findById(id);
        appeal.setArchivedDate(ZonedDateTime.now());
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(APPEAL_URL)
                .setPath("/move/")
                .setPath(String.valueOf(id));
        restTemplate.put(builder.build(), appeal);
    }

    /**
     * Нахождение обращения по id не из архива
     */
    @Override
    public AppealDto findByIdNotArchived(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(APPEAL_URL)
                .setPath("/findByIdNotArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), AppealDto.class);
    }

    /**
     * Нахождение всех обращений не из архива
     */
    @Override
    public Collection<AppealDto> findAllNotArchived() throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(APPEAL_URL)
                .setPath("/findByIdNotArchived");
        return restTemplate.getForObject(builder.build(), Collection.class);
    }
}
