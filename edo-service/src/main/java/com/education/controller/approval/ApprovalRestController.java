package com.education.controller.approval;

import com.education.service.approval.ApprovalService;
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

import static model.constant.Constant.APPROVAL_SERVICE_URL;
import static model.constant.Constant.APPROVAL_URL;

@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Rest-контроллер для ApprovalDto, который отправляет запросы от клиентов к сервисам edo-repository")
@RequestMapping(APPROVAL_SERVICE_URL)
public class ApprovalRestController {
    private final ApprovalService approvalService;

    @Operation(summary = "Добавляет новую запись в таблицу Approval")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApprovalDto> save(@RequestBody ApprovalDto approvalDto) {
        log.info("Send a post-request to post new Approval to database");
        ApprovalDto savedApproval = approvalService.save(approvalDto);
        log.info("Response: {} was added to database", approvalDto);
        return new ResponseEntity<>(savedApproval, HttpStatus.CREATED);
    }


    @Operation(summary = "Возвращает лист согласования по id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApprovalDto> findById(@PathVariable("id") long id) {
        log.info("Send a get-request to get Approval with id = {} from database", id);
        var approvalDto = approvalService.findById(id);
        log.info("Response from database: {}", approvalDto);
        return new ResponseEntity<>(approvalDto, HttpStatus.OK);
    }


    @Operation(summary = "Возвращает все листы согласования")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ApprovalDto>> findAll() {
        log.info("Send a get-request to get all approvals from database");
        var listApprovalDto = approvalService.findAll();
        log.info("Response from database: {}", listApprovalDto);
        return new ResponseEntity<>(listApprovalDto, HttpStatus.OK);
    }

    @Operation(summary = "Возвращает все листы согласования по списку id")
    @GetMapping("/all/{ids}")
    private ResponseEntity<Collection<ApprovalDto>> findAllById(@PathVariable List<Long> ids) {
        log.info("Send a get-request to get all approvals from database by ids");
        var listApprovalDto = approvalService.findAllById(ids);
        log.info("Response from database: {}", listApprovalDto);
        return new ResponseEntity<>(listApprovalDto, HttpStatus.OK);
    }


    @Operation(summary = "Удаляет лист согласования")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        log.info("Send a delete-request to delete approval with id = {} from database", id);
        approvalService.delete(id);
        log.info("Response: approval with id = {} was deleted from database", id);
        return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "В строке таблицы Approval заполняет поле archived_date")
    @PatchMapping(value = "/move/{id}")
    public ResponseEntity<ApprovalDto> moveToArchive(@PathVariable Long id) {
        log.info("Adding archived date {} in approval with id: {}", ZonedDateTime.now(), id);
        approvalService.moveToArchive(id);
        log.info("Moving approval with id: {} to archive", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Находит строку таблицы Approval c полем acrhivedDate = null, по заданному id")
    @GetMapping(value = "/findByIdNotArchived/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApprovalDto> findByIdNotArchived(@PathVariable Long id) {
        log.info("Getting from database approval with field archived_date = null, with id: {}", id);
        var approvalDto = approvalService.findByIdNotArchived(id);
        log.info("Approval: {}", approvalDto);
        return new ResponseEntity<>(approvalDto, HttpStatus.OK);
    }

    @Operation(summary = "Находит все листы согласования с полем acrhivedDate = null")
    @GetMapping(value = "/findAllNotArchived", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ApprovalDto>> findAllNotArchived() {
        log.info("Getting from database all approvals with field archived_date = null");
        var listApprovalDto = approvalService.findAllNotArchived();
        log.info("List of approvals: {}", listApprovalDto);
        return new ResponseEntity<>(listApprovalDto, HttpStatus.OK);
    }

    // findByIdInAndArchivedDateNull
    @Operation(summary = "Возвращает все не перемещенные в архив листы согласования по списку id")
    @GetMapping(value = "/findByIdInAndArchivedDateNull/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ApprovalDto>> findByIdInAndArchivedDateNull(@PathVariable List<Long> ids) {
        log.info("Getting from database all approvals with field archived_date = null and with ids");
        var listApprovalDto = approvalService.findByIdInAndArchivedDateNull(ids);
        log.info("List of approvals: {}", listApprovalDto);
        return new ResponseEntity<>(listApprovalDto, HttpStatus.OK);
    }

    @Operation(summary = "Направляет лист согласования")
    @PostMapping(value = "/send/{id}")
    public ResponseEntity<ApprovalDto> sendForApproval(@PathVariable Long id) {
        log.info("Send a post-request to update approval with id: {} from database", id);
        approvalService.sendForApproval(id);
        log.info("Send approval with id: {} ", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
