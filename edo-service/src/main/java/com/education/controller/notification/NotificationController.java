package com.education.controller.notification;

import com.education.service.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.NotificationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/service/notification")
@Tag(name = "Rest- контрллер для работы с уведомлениями")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "Получить настройки уведомлений по id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDto> findById(@PathVariable Long id) {
        log.info("Sending response with notification of assigned ID");
        NotificationDto notificationDto = notificationService.findById(id);
        log.info("Operation was successful, got notification by ID={}", id);
        return new ResponseEntity<>(notificationDto, HttpStatus.OK);
    }

    @Operation(summary = "Получить все настройки уведомлений")
    @GetMapping("/all")
    public ResponseEntity<Collection<NotificationDto>> findAll() {
        log.info("Sending response with all notifications");
        Collection<NotificationDto> notificationDto = notificationService.findAll();
        log.info("Operation was successful, got all notifications");
        return new ResponseEntity<>(notificationDto, HttpStatus.OK);
    }

    @Operation(summary = "Получение настроек уведомлений по IDs")
    @GetMapping("/all/{ids}")
    public ResponseEntity<Collection<NotificationDto>> findAllById(@PathVariable List<Long> ids) {
        log.info("Sending response with notifications of assigned IDs");
        Collection<NotificationDto> notificationDto = notificationService.findAllById(ids);
        log.info("Operation was successful, got notifications by IDs={}", ids);
        return new ResponseEntity<>(notificationDto, HttpStatus.OK);
    }

    @Operation(summary = "Сохранение настроек уведомлений")
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDto> save(@RequestBody NotificationDto notificationDto) {
        log.info("Saving notification settings");
        NotificationDto savedNotificationDto = notificationService.save(notificationDto);
        log.info("Operation was successful, saved notification with ID={}", savedNotificationDto.getId());
        return new ResponseEntity<>(savedNotificationDto, HttpStatus.OK);
    }

    @Operation(summary = "Удаление настроек уведомлений по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting notification settings with ID={}", id);
        notificationService.delete(id);
        log.info("Operation was successful, deleted notification with ID={}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
