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

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;
import static model.enum_.Status.NEW_STATUS;

/**
 * Сервис-слой для Appeal
 */
@Service
@AllArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final RestTemplate restTemplate;

    private final AuthorService authorService;
    private final QuestionService questionService;
    private final FilePoolService filePoolService;
    private final String URL = "http://edo-repository/api/repository/appeal";


    /**
     * Нахождение обращения по id
     */
    @Override
    public AppealDto findById(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, APPEAL_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), AppealDto.class);
    }

    /**
     * Нахождение всех обращений
     */
    @Override
    public Collection<AppealDto> findAll() {
        var builder = buildURI(EDO_REPOSITORY_NAME, APPEAL_URL);
        return restTemplate.getForObject(builder.toString(), Collection.class);
    }

    /**
     * Изменение обращения, добавление status,маппинг  Authors,Questions
     */
    @Override
    public AppealDto save(AppealDto appealDto) {
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
                            try {
                                questionDto = questionService.save(questionDto);
                            } catch (URISyntaxException e) {
                                throw new RuntimeException(e);
                            }
                            savedQuestions.add(questionDto);
                        }
                        return questionDto;
                    })
                    .collect(Collectors.toList()));

            // Сохранение новых файлов
            appealDto.setFile(appealDto.getFile().stream()
                    .map(filePoolDto -> {
                        if (filePoolDto.getId() == null) {
                            try {
                                filePoolDto = filePoolService.save(filePoolDto);
                            } catch (URISyntaxException e) {
                                throw new RuntimeException(e);
                            }
                            savedFiles.add(filePoolDto);
                        }
                        return filePoolDto;
                    })
                    .collect(Collectors.toList()));


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            var builder = buildURI(EDO_REPOSITORY_NAME, APPEAL_URL)
                    .setPath("/");

            return restTemplate.getForObject(builder.toString(), AppealDto.class);
        } catch (Exception e) {

            // Удаление сохранённых вложенных сущностей
            savedAuthors.forEach(authorDto -> {
                try {
                    authorService.delete(authorDto.getId());
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            });
            savedFiles.forEach(filePoolDto -> {
                try {
                    filePoolService.delete(filePoolDto.getId());
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            });
            savedQuestions.forEach(questionDto -> {
                try {
                    questionService.delete(questionDto.getId());
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            });

            throw e;
        }
    }

    /**
     * Удаления обращения по Id
     */
    @Override
    public void delete(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, APPEAL_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.delete(builder.toString());
    }

    /**
     * Перенос обращения в архив по id
     */
    @Override
    public void moveToArchive(Long id) {
        AppealDto appeal = findById(id);
        appeal.setArchivedDate(ZonedDateTime.now());
        var builder = buildURI(EDO_REPOSITORY_NAME, APPEAL_URL)
                .setPath("/move/")
                .setPath(String.valueOf(id));
        restTemplate.put(builder.toString(), appeal);
    }

    /**
     * Нахождение обращения по id не из архива
     */
    @Override
    public AppealDto findByIdNotArchived(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, APPEAL_URL)
                .setPath("/findByIdNotArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), AppealDto.class);
    }

    /**
     * Нахождение всех обращений не из архива
     */
    @Override
    public Collection<AppealDto> findAllNotArchived() {
        var builder = buildURI(EDO_REPOSITORY_NAME, APPEAL_URL)
                .setPath("/findByIdNotArchived");
        return restTemplate.getForObject(builder.toString(), Collection.class);
    }
}
