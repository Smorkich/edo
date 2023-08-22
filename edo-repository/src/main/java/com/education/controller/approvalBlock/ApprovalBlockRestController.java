package com.education.controller.approvalBlock;

import com.education.entity.ApprovalBlock;
import com.education.service.approvalBlock.ApprovalBlockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.ApprovalBlockDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static com.education.mapper.ApprovalBlockMapper.APPROVAL_BLOCK_MAPPER;

/**
 * Rest-контроллер сущности ApprovalBlock для отправки запросов от клиентов к БД
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/repository/approvalBlock")
@Tag(name = "Rest-контроллер для ApprovalBlockDto, который отправляет запросы от клиентов к сервисам edo-service")
public class ApprovalBlockRestController {
    private final ApprovalBlockService approvalBlockService;

    @Operation(summary = "Добавляет новую запись в таблицу approval_block")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApprovalBlockDto> save(@RequestBody ApprovalBlockDto approvalBlockDto) {
        log.info("Send a post-request to post new ApprovalBlock to database");
        ApprovalBlock savedApprovalBlock = approvalBlockService.save(APPROVAL_BLOCK_MAPPER.toEntity(approvalBlockDto));
        log.info("Response: {} was added to database", approvalBlockDto);
        return new ResponseEntity<>(APPROVAL_BLOCK_MAPPER.toDto(savedApprovalBlock), HttpStatus.CREATED);
    }

    @Operation(summary = "Возвращает блок листа согласования по id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApprovalBlockDto> findById(@PathVariable("id") long id) {
        log.info("Send a get-request to get member of approval with id = {} from database", id);
        var approvalBlockDto = APPROVAL_BLOCK_MAPPER.toDto(approvalBlockService.findById(id));
        log.info("Response from database: {}", approvalBlockDto);
        return new ResponseEntity<>(approvalBlockDto, HttpStatus.OK);
    }

    @Operation(summary = "Возвращает все блоки листа согласования")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ApprovalBlockDto>> findAll() {
        log.info("Send a get-request to get all approval blocks from database");
        var listApprovalBlockDto = APPROVAL_BLOCK_MAPPER.toDto(approvalBlockService.findAll());
        log.info("Response from database: {}", listApprovalBlockDto);
        return new ResponseEntity<>(listApprovalBlockDto, HttpStatus.OK);
    }

    @Operation(summary = "Возвращает все блоки листа согласования по списку id")
    @GetMapping("/all/{ids}")
    private ResponseEntity<Collection<ApprovalBlockDto>> findAllById(@PathVariable List<Long> ids) {
        log.info("Send a get-request to get all approval blocks from database by ids");
        var listApprovalBlockDto = APPROVAL_BLOCK_MAPPER.toDto(approvalBlockService.findAllById(ids));
        log.info("Response from database: {}", listApprovalBlockDto);
        return new ResponseEntity<>(listApprovalBlockDto, HttpStatus.OK);
    }

    @Operation(summary = "Удаляет блок листа согласования")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        log.info("Send a delete-request to delete approval block with id = {} from database", id);
        approvalBlockService.delete(id);
        log.info("Response: approval block with id = {} was deleted from database", id);
        return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Добавляет новую запись в таблицу approval_block со ссылкой на Approval")
    @PostMapping(value = "saveWithLinkToApproval/{approvalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApprovalBlockDto> saveWithLinkToApproval(@RequestBody ApprovalBlockDto approvalBlockDto, @PathVariable Long approvalId) {
        log.info("Send a post-request to post new ApprovalBlock to database with link to Approval");
        ApprovalBlock savedApprovalBlock = approvalBlockService.saveWithLinkToApproval(APPROVAL_BLOCK_MAPPER.toEntity(approvalBlockDto), approvalId);
        log.info("Response: {} was added to database", approvalBlockDto);
        return new ResponseEntity<>(APPROVAL_BLOCK_MAPPER.toDto(savedApprovalBlock), HttpStatus.CREATED);
    }
}
