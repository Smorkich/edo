package com.education.service.appeal.impl;

import com.education.service.appeal.AppealService;
import com.education.service.author.AuthorService;
import com.education.service.facsimile.FacsimileService;
import com.education.service.filePool.FilePoolService;
import com.education.service.nomenclature.NomenclatureService;
import com.education.service.minio.MinioService;
import com.education.service.question.QuestionService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.dto.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.*;
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
    private final FacsimileService facsimileService;
    private final MinioService minioService;

    private final NomenclatureService nomenclatureService;


    /**
     * Нахождение обращения по id
     */
    @Override
    public AppealDto findById(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, APPEAL_URL + "/" + id);
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
        if (appealDto.getAppealsStatus() == null) {
            appealDto.setAppealsStatus(NEW_STATUS);
            appealDto.setCreationDate(ZonedDateTime.now());
        }
        // Списки, которые хранят, новые сущности
        List<AuthorDto> savedAuthors = new ArrayList<>();
        List<QuestionDto> savedQuestions = new ArrayList<>();
        List<FilePoolDto> savedFiles = new ArrayList<>();

        try {
            NomenclatureDto nomenclatureDto = appealDto.getNomenclature();
            Calendar calendar = Calendar.getInstance();
            var year = calendar.get(Calendar.YEAR) % 100;
            var number = nomenclatureDto.getTemplate()
                    .replace("%ИНДЕКС", nomenclatureDto.getIndex())
                    .replace("%ГОД", Integer.toString(year))
                    .replace("%НОМЕР", Long.toString(nomenclatureDto.getCurrentValue()));
            appealDto.setNumber(number);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // Сохранение новых авторов
            appealDto.setAuthors(appealDto.getAuthors().stream()
                    .map(authorDto -> {
                        if (authorDto.getId() == null) {
                            authorDto = authorService.save(authorDto);
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
            String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "api/repository/appeal").toString();
            return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(appealDto, headers), AppealDto.class).getBody();

        } catch (Exception e) {

            // Удаление сохранённых вложенных сущностей
            savedAuthors.forEach(authorDto -> {
                authorService.delete(authorDto.getId());
            });
            savedFiles.forEach(filePoolDto -> {
                filePoolService.delete(filePoolDto.getId());
            });
            savedQuestions.forEach(questionDto -> {
                questionService.delete(questionDto.getId());
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

    /**
     * По принятому обращению формирует valueMapForSendingObjects для отправки на
     * EDO_INTEGRATION для последующей рассылки сообщений
     */
    @Override
    public void sendMessage(AppealDto appealDto) {
        var builder = buildURI(EDO_INTEGRATION_NAME, MESSAGE_URL);
        var valueMapForSendingObjects = new LinkedMultiValueMap<>();

        //Сбор всех emails для отправки
        List<String> emails = new ArrayList<>();
        emails.addAll(getEmployeesEmails(appealDto.getAddressee()));
        emails.addAll(getEmployeesEmails(appealDto.getSigner()));

        //Получение URL
        var builderForAppealUrl = buildURI(EDO_REST_NAME, APPEAL_REST_URL + "/" + appealDto.getId());

        //Заполнение мапы данными
        valueMapForSendingObjects.add("appealURL", builderForAppealUrl.toString());
        valueMapForSendingObjects.addAll("emails", emails);
        valueMapForSendingObjects.add("appealNumber", appealDto.getNumber());

        HttpHeaders headers = new HttpHeaders();
        var requestEntity = new HttpEntity<>(valueMapForSendingObjects, headers);

        restTemplate.postForEntity(builder.toString(), requestEntity, Object.class);
    }

    /**
     * Метод достает Appeal по Questions id
     */
    @Override
    public AppealDto findAppealByQuestionsId(Long id) {
        String URL = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "api/repository/appeal/findAppealByQuestionsId/" + id).toString();
        return restTemplate.getForObject(URL, AppealDto.class);

    }

    /**
     * Метод закрепляет файл за обращением
     */

    @Override
    public AppealDto upload(Long id, FilePoolDto file) {
        AppealDto appealDto = findById(id);
        try (InputStream facsimile = new FileInputStream("FacsimilePic.png")) {
            minioService.overlayFacsimileOnFirstFile(file.getStorageFileId(), file.getExtension(), "application/pdf", facsimile);
        } catch (IOException e) {
            System.out.printf("Факсимиле не было наложено на файл");
        }
        appealDto.getFile().add(file);
        var save = save(appealDto);
        return save;
    }

    /**
     * Метод достает emails из коллекции EmployeeDto
     */
    private Collection<String> getEmployeesEmails(Collection<EmployeeDto> employees) {

        return employees.stream().filter(Objects::nonNull)
                .map(emp -> {
                    var builderEmployee = buildURI(EDO_REPOSITORY_NAME, EMPLOYEE_URL + "/" + emp.getId());
                    var employeeDto = restTemplate.getForObject(builderEmployee.toString(), EmployeeDto.class);
                    return employeeDto.getEmail();
                })
                .collect(Collectors.toList());
    }
}
