package com.education.controller.approval;

import com.education.entity.Approval;
import com.education.service.author.approval.ApprovalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.ApprovalDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import static com.education.mapper.ApprovalMapper.APPROVAL_MAPPER;
import static model.constant.Constant.APPROVAL_URL;

/**
 * Rest-контроллер сущности Approval для отправки запросов от клиентов к БД
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping(APPROVAL_URL)
@Tag(name = "Rest-контроллер для ApprovalDto, который отправляет запросы от клиентов к сервисам edo-service")
public class ApprovalRestController {
    private final ApprovalService approvalService;

    @Operation(summary = "Добавляет новую запись в таблицу Approval")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApprovalDto save(@RequestBody ApprovalDto approvalDto) {
        log.info("Send a post-request to post new Approval to database");
        approvalService.save(APPROVAL_MAPPER.toEntity(approvalDto));
        log.info("Response: {} was added to database", approvalDto);
        return approvalDto;
    }

    @Operation(summary = "Возвращает лист согласования по id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApprovalDto findById(@PathVariable("id") long id) {
        log.info("Send a get-request to get Approval with id = {} from database", id);
        var approvalDto = APPROVAL_MAPPER.toDto(approvalService.findById(id));
        log.info("Response from database: {}", approvalDto);
        return approvalDto;
    }

    @Operation(summary = "Возвращает все листы согласования")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ApprovalDto> findAll() {
        log.info("Send a get-request to get all approvals from database");
        var listApprovalDto = APPROVAL_MAPPER.toDto(approvalService.findAll());
        log.info("Response from database: {}", listApprovalDto);
        return listApprovalDto;
    }

    @Operation(summary = "Возвращает все листы согласования по списку id")
    @GetMapping("/all/{ids}")
    private Collection<ApprovalDto> findAllById(@PathVariable List<Long> ids) {
        log.info("Send a get-request to get all approvals from database by ids");
        var listApprovalDto = APPROVAL_MAPPER.toDto(approvalService.findAllById(ids));
        log.info("Response from database: {}", listApprovalDto);
        return listApprovalDto;
    }

    @Operation(summary = "Удаляет лист согласования")
    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        log.info("Send a delete-request to delete approval with id = {} from database", id);
        approvalService.delete(id);
        log.info("Response: approval with id = {} was deleted from database", id);
        return HttpStatus.ACCEPTED;
    }

    @Operation(summary = "В строке таблицы Approval заполняет поле archived_date")
    @PatchMapping(value = "/move/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String moveToArchive(@PathVariable Long id) {
        log.info("Adding archived date {} in approval with id: {}", ZonedDateTime.now(), id);
        approvalService.moveToArchive(id);
        log.info("Moving approval with id: {} to archive", id);
        return "The file is archived";
    }

    @Operation(summary = "Находит строку таблицы Approval c полем acrhivedDate = null, по заданному id")
    @GetMapping(value = "/findByIdNotArchived/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApprovalDto findByIdNotArchived(@PathVariable Long id) {
        log.info("Getting from database approval with field archived_date = null, with id: {}", id);
        return APPROVAL_MAPPER.toDto(approvalService.findByIdNotArchived(id));
    }

    @Operation(summary = "Находит все листы согласования с полем acrhivedDate = null")
    @GetMapping(value = "/findAllNotArchived", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ApprovalDto> findAllNotArchived() {
        log.info("Getting from database all approvals with field archived_date = null");
        return APPROVAL_MAPPER.toDto(approvalService.findAllNotArchived());
    }

    // findByIdInAndArchivedDateNull
    @Operation(summary = "Возвращает все не перемещенные в архив листы согласования по списку id")
    @GetMapping(value = "/findByIdInAndArchivedDateNull/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ApprovalDto> findByIdInAndArchivedDateNull(@PathVariable List<Long> ids) {
        log.info("Getting from database all approvals with field archived_date = null and with ids");
        return APPROVAL_MAPPER.toDto(approvalService.findByIdInAndArchivedDateNull(ids));
    }

    @Operation(summary = "Обновляет запись в таблице Approval")
    @PostMapping(value = "/update")
    public String update(@RequestBody ApprovalDto approvalDto) {
        log.info("Send a post-request to update Approval from database");
        approvalService.update(APPROVAL_MAPPER.toEntity(approvalDto));
        log.info("Response: {} was updated to database", approvalDto);
        return "The file was updated";
    }
}
