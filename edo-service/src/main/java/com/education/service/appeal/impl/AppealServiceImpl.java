package com.education.service.appeal.impl;

import com.education.controller.facsimile.FacsimileController;
import com.education.feign.AppealFeignClient;
import com.education.feign.ResolutionFeignClient;
import com.education.service.appeal.AppealService;
import com.education.service.author.AuthorService;
import com.education.service.facsimile.FacsimileService;
import com.education.service.filePool.FilePoolService;
import com.education.service.minio.MinioService;
import com.education.service.nomenclature.NomenclatureService;
import com.education.service.question.QuestionService;
import com.education.util.URIBuilderUtil;
import com.education.util.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.dto.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;
import static model.enum_.Status.*;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

/**
 * Сервис-слой для Appeal
 */
@Service
@AllArgsConstructor
@Slf4j
public class AppealServiceImpl implements AppealService {

    private final RestTemplate restTemplate;
    private final AuthorService authorService;
    private final QuestionService questionService;
    private final FilePoolService filePoolService;
    private final FacsimileService facsimileService;
    private final FacsimileController facsimileController;
    private final MinioService minioService;
    private final NomenclatureService nomenclatureService;
    private final ResolutionFeignClient resolutionFeignClient;
    private final AppealFeignClient appealFeignClient;


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
     * Метод сохраняет или изменяет AppealDto передаваемый в параметре
     * <p> Назначает статус, время создания и number. Если произошли изменения меняет статус
     * обращения и всех его вопросов на "UPDATED". После всего этого передаёт appeal в контроллер edo-repository
     *
     * @param appealDto - DTO который нужно сохранить или изменить
     * @return AppealDto - DTO который сохранили или изменили
     */
    @Override
    public AppealDto save(AppealDto appealDto) {
        // Вызываем валидацию перед сохранением обращения
        Validator.getValidateAppeal(appealDto);
        // Назначение статуса и времени создания
        setNewAppealStatusAndCreationDate(appealDto);
        // Генерация и назначения number Appeal'у
        nomenclatureService.generateAppealNumber(appealDto);
        // Сохранение или редактирование новых сущностей Appeal'a
        try {
            if (appealDto.getAuthors() == null) {
                appealDto.setAuthors(authorService.saveAll(appealDto.getAuthors()));
            }
            if (appealDto.getQuestions() == null) {
                appealDto.setQuestions(questionService.saveAll(appealDto.getQuestions()));
            }
            if (appealDto.getFile() == null) {
                appealDto.setFile(filePoolService.saveAll(appealDto.getFile()));
            }
            // Отправление пост запроса в репозиторий
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "api/repository/appeal").toString();
            return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(appealDto, headers), AppealDto.class).getBody();
        } catch (Exception e) {
            // Удаление сохранённых вложенных сущностей если возникла ошибка
            appealDto.getAuthors().stream().filter(authorDto -> authorDto.getId() != null).forEach(authorDto -> authorService.delete(authorDto.getId()));
            appealDto.getQuestions().stream().filter(questionDto -> questionDto.getId() != null).forEach(filePoolDto -> filePoolService.delete(filePoolDto.getId()));
            appealDto.getFile().stream().filter(filePoolDto -> filePoolDto.getId() != null).forEach(questionDto -> questionService.delete(questionDto.getId()));
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
     * Накладывает факсимиле на файл за обращения
     */
    @Override
    public AppealDto upload(Long id, FilePoolDto file) {
        AppealDto appealDto = findById(id);
        var employeeId = appealDto.getSigner().iterator().next().getId();
        Resource facsimileRes = facsimileService.getFacsimile(facsimileController.findFacsimileByEmployeeId(employeeId).getBody());
        try (var facsimileFile = facsimileRes.getInputStream()) {
            minioService.overlayFacsimileOnFirstFile(file.getStorageFileId(), file.getExtension(), APPLICATION_PDF_VALUE, facsimileFile);
        } catch (IOException e) {
            log.error("Факсимиле не было наложено на файл");
        }
        appealDto.getFile().add(file);
        return save(appealDto);
    }

    /**
     * Добавляет статус REGISTERED (зарегистрировано) обращению с id, который передаётся в параметре, а так-же
     * вопросам этого обращения.
     * <p> Логика работы:
     * <p> Создаём коллекцию appealQuestions и ищем туда все вопросы относящиеся к данному обращению (по id).
     * <p> Достаём id каждого вопроса из коллекции и складываем в questionsIds
     * <p> Вызываем registerAllQuestions() - отправляет коллекцию id вопросов на регистрацию в edo-repository
     * через QuestionService edo-service
     * <p> Отправляем запрос в edo-repository на регистрацию Appeal по id, который передаётся в параметре
     *
     * @param id идентификатор регистрируемого Appeal
     * @return AppealDto - DTO сущности Appeal (обращение)
     */
    @Override
    public AppealDto register(Long id) {
        var appealQuestions = questionService.findByAppealId(id);
        var questionsIds = appealQuestions.stream()
                .map(QuestionDto::getId)
                .collect(Collectors.toList());
        questionService.registerAllQuestions(questionsIds);
        var uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/appeal/register");
        uri.setParameter("id", String.valueOf(id));
        return restTemplate.postForObject(uri.toString(), new HttpEntity<>(new HttpHeaders()), AppealDto.class);
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

    /**
     * Метод для изменения статуса вопросов Appeal'a на "UPDATED"
     * <p>Вспомогательный метод к методу "save"
     *
     * @param appealDto dto обращения (Appeal), статус которого хотим изменить на "UPDATED"
     */
    private void setUpdatedStatusQuestionsForAppeal(AppealDto appealDto) {
        Collection<QuestionDto> questionDtos = appealDto.getQuestions();
        Collection<Long> questionIds = questionDtos.stream().map(QuestionDto::getId).toList();
        questionService.setStatusUpdatedAll(questionIds);
    }

    /**
     * Метод, который устанавливает Appeal'у дату его создания на "now"(сейчас) и добавляет статус - "NEW_STATUS"
     * <p>Вспомогательный метод к методу "save"
     *
     * @param appealDto обращение которому мы хотим установить дату создания "now" и статус - "NEW_STATUS"
     */
    private void setNewAppealStatusAndCreationDate(AppealDto appealDto) {
        AppealDto appealFromDb = findById(appealDto.getId());
        if (appealDto.getAppealsStatus() == null) {
            appealDto.setAppealsStatus(NEW_STATUS);
            appealDto.setCreationDate(ZonedDateTime.now());
        } else if (!appealFromDb.equals(appealDto) && appealDto.getAppealsStatus().equals(REGISTERED)) {
            setUpdatedStatusQuestionsForAppeal(appealDto);
            appealDto.setAppealsStatus(UPDATED);
        }
    }

    /**
     * Если резолюция становится единственной для обращения и её статус isDraft = false
     * То меняем статус обращения на UNDER_CONSIDERATION
     */
    @Override
    public void setNewAppealStatusIfResolutionLastAndIsDraftFalse(AppealDto appealDto) {
        Collection<ResolutionDto> resolutions = resolutionFeignClient.findAllByAppealIdAndIsDraftFalse(appealDto.getId());
        if (resolutions.size() == 1) {
            appealDto.setAppealsStatus(UNDER_CONSIDERATION);
        }
    }

    @Override
    public Collection<AppealFileDto> findAllByAppealIdForXLSX(Long appealId) {
        return appealFeignClient.findAllByAppealIdForXLSX(appealId);
    }

}
