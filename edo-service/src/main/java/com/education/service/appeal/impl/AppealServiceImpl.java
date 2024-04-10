package com.education.service.appeal.impl;

import com.education.controller.facsimile.FacsimileController;
import com.education.feign.AppealFeignClient;
import com.education.feign.AppealFeignClientToIntegrationEdo;
import com.education.feign.ResolutionFeignClient;
import com.education.service.appeal.AppealService;
import com.education.service.author.AuthorService;
import com.education.service.common.AbstractService;
import com.education.service.facsimile.FacsimileService;
import com.education.service.filePool.FilePoolService;
import com.education.service.minio.MinioService;
import com.education.service.nomenclature.NomenclatureService;
import com.education.service.question.QuestionService;
import com.education.util.Validator;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.dto.*;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;
import static model.enum_.Status.*;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

/**
 * Сервис-слой для Appeal
 */
@Slf4j
@Service
@AllArgsConstructor
public class AppealServiceImpl extends AbstractService<AppealDto> implements AppealService {

    private final AppealFeignClient appealFeignClient;
    private final AppealFeignClientToIntegrationEdo massageFeignClient;

    private final AuthorService authorService;
    private final QuestionService questionService;
    private final FilePoolService filePoolService;
    private final FacsimileService facsimileService;
    private final FacsimileController facsimileController;
    private final MinioService minioService;
    private final NomenclatureService nomenclatureService;
    private final ResolutionFeignClient resolutionFeignClient;


    /**
     * Метод сохраняет или изменяет AppealDto передаваемый в параметре
     * <p> Назначает статус, время создания и number. Если произошли изменения меняет статус
     * обращения и всех его вопросов на "UPDATED". После всего этого передаёт appeal в контроллер edo-repository
     *
     * @param appealDto - DTO который нужно сохранить или изменить
     * @return - DTO который сохранили или изменили
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
            if (appealDto.getAuthors() != null) {
                appealDto.setAuthors(authorService.saveAll(appealDto.getAuthors()));
            }
            if (appealDto.getQuestions() != null) {
                appealDto.setQuestions(questionService.saveAll(appealDto.getQuestions()));
            }
            if (appealDto.getFile() != null) {
                appealDto.setFile(filePoolService.saveAll(appealDto.getFile()));
            }

            return appealFeignClient.save(appealDto);
        } catch (Exception e) {
            // Удаление сохранённых вложенных сущностей если возникла ошибка
            if (appealDto.getAuthors() != null) {
                appealDto.getAuthors().stream().filter(authorDto -> authorDto.getId() != null)
                        .forEach(authorDto -> authorService.delete(authorDto.getId()));
            }
            if (appealDto.getQuestions() != null) {
                appealDto.getQuestions().stream().filter(questionDto -> questionDto.getId() != null)
                        .forEach(filePoolDto -> filePoolService.delete(filePoolDto.getId()));
            }
            if (appealDto.getFile() != null) {
                appealDto.getFile().stream().filter(filePoolDto -> filePoolDto.getId() != null)
                        .forEach(questionDto -> questionService.delete(questionDto.getId()));
            }
            throw e;
        }
    }

    /**
     * Перенос обращения в архив по id
     */
    @Override
    public void moveToArchive(Long id) {
        AppealDto appeal = findById(id);
        if (appeal == null) {
            throw new NotFoundException("Appeal with ID " + id + " not found");
        }
        appeal.setArchivedDate(ZonedDateTime.now());
        appealFeignClient.moveToArchive(id);
    }

    /**
     * Нахождение обращения по id не из архива
     */
    @Override
    public AppealDto findByIdNotArchived(Long id) {
        AppealDto appeal = appealFeignClient.findByIdNotArchived(id);
        if (appeal == null || appeal.getAppealsStatus() == ARCHIVE) {
            throw new NotFoundException("Appeal with ID " + id + " not found or already archived");
        }
        return appeal;
    }

    /**
     * Нахождение всех обращений не из архива
     */
    @Override
    public Collection<AppealDto> findAllNotArchived() {
        try {
            return appealFeignClient.findAllNotArchived();
        } catch (Exception e) {
            log.error("Unexpected error fetching not archived appeals: " + e.getMessage());
            return Collections.emptyList();
        }

    }

    /**
     * По принятому обращению формирует valueMapForSendingObjects для отправки на
     * EDO_INTEGRATION для последующей рассылки сообщений
     */
    @Override
    public void sendMessage(AppealDto appealDto) {
        try {
            //Сбор всех emails для отправки
            List<String> emails = new ArrayList<>();
            emails.addAll(getEmployeesEmails(appealDto.getAddressee()));
            emails.addAll(getEmployeesEmails(appealDto.getSigner()));

            //Получение URL
            var builderForAppealUrl = buildURI(EDO_REST_NAME, APPEAL_REST_URL + "/" + appealDto.getId());

            //Заполнение мапы данными
            MultiValueMap<String, Object> valueMapForSendingObjects = new LinkedMultiValueMap<>();
            valueMapForSendingObjects.add("appealURL", builderForAppealUrl.toString());
            valueMapForSendingObjects.addAll("emails", emails);
            valueMapForSendingObjects.add("appealNumber", appealDto.getNumber());

            massageFeignClient.sendMessage(valueMapForSendingObjects.toSingleValueMap());
        } catch (Exception e) {
            log.error("Failed to send message", e);
        }
    }

    /**
     * Метод достает Appeal по Questions id
     */
    @Override
    public AppealDto findAppealByQuestionsId(Long id) {
        return appealFeignClient.findAppealByQuestionsId(id);
    }

    /**
     * Метод закрепляет файл за обращением.
     * Накладывает факсимиле на файл обращения
     */
    @Override
    public AppealDto upload(Long id, FilePoolDto file) {
        AppealDto appealDto = findById(id);
        var employeeId = appealDto.getSigner().iterator().next().getId();
        Resource facsimileRes = facsimileService.getFacsimile(
                facsimileController.findFacsimileByEmployeeId(employeeId).getBody());
        try (var facsimileFile = facsimileRes.getInputStream()) {
            minioService.overlayFacsimileOnFirstFile(file.getStorageFileId(), file.getExtension(),
                    APPLICATION_PDF_VALUE, facsimileFile);
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
                .toList();
        questionService.registerAllQuestions(questionsIds);
        return appealFeignClient.register(id);
    }

    /**w
     * Метод достает emails из коллекции EmployeeDto
     */
    private Collection<String> getEmployeesEmails(Collection<EmployeeDto> employees) {
        return employees.stream()
                .filter(Objects::nonNull)
                .map(EmployeeDto::getEmail)
                .toList();
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
        Collection<ResolutionDto> resolutions = resolutionFeignClient.findAllByAppealIdAndIsDraftFalse(
                appealDto.getId());
        if (resolutions.size() == 1) {
            appealDto.setAppealsStatus(UNDER_CONSIDERATION);
        }
    }

    /**
     * Если все резолюции обращения выполнены, то статус обращения меняется на PERFORMED
     */

    @Override
    public void updateAppealStatusWhereExecutionStatusIsPerformed(Long resolutionId) {
        appealFeignClient.updateAppealStatusWhereExecutionStatusIsPerformed(resolutionId);
    }

    /**
     * First it checks that the resolution was the last one to appeal, then it changes the appealStatus of appeal
     * @param resolutionId - id of the archived resolution
     */
    @Override
    public void setAppealStatusIfLastResolutionArchived(Long resolutionId) {
            appealFeignClient.setAppealStatusIfLastResolutionArchived(resolutionId);
    }

}
