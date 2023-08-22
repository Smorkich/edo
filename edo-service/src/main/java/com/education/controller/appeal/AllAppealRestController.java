package com.education.controller.appeal;

import com.education.service.appeal.AllAppealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AllAppealDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Обращения", description = "Методы для работы с обращениями")
@RequestMapping("/api/service/allAppeals")
public class AllAppealRestController {

    private AllAppealService allAppealService;


    @Operation(summary = "Получает все обращения", description = "Строка в Appeal должна существовать")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AllAppealDto>> getAllAppeals(@RequestParam("creatorId") Long creatorId, @RequestParam("start") int start, @RequestParam("end") int end) {
        log.info("Getting from database all appeals");
        var appealDtoCollection = allAppealService.getAllAppeals(creatorId, start, end);
        log.info("Appeals: {}", appealDtoCollection);
        return new ResponseEntity<>(appealDtoCollection, HttpStatus.OK);
    }


}
