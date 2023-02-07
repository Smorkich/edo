package com.education.controller.appeal;

import com.education.service.appeal.AllAppealService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AllAppealDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest-контроллер в "edo-rest", служит для отправки обращения (Appeal) в БД используя RestTemplate
 */
@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api/edo/allAppeals")
public class AllAppealController {
    private final AllAppealService allAppealService;

    @ApiOperation(value = "Отправляет запрос на получение в edo-service", notes = "Обращение должен существовать")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AllAppealDto> getAllAppeals(@RequestParam("lastUser") int lastUser, @RequestParam("numberOfUsersToDisplay") int numberOfUsersToDisplay) {
        log.info("Отправить get-запрос в edo-service");
        var getAll = allAppealService.getAllAppeals(lastUser, numberOfUsersToDisplay);
        log.info(" get-запрос отправлен в edo-service");
        return new ResponseEntity<>(getAll, HttpStatus.CREATED);
    }

}
