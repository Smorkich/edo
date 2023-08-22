package com.education.controller.appeal;

import com.education.service.appeal.AllAppealService;
import com.education.util.EmployeeBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AllAppealDto;
import model.dto.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Rest-контроллер в "edo-rest", служит для отправки обращения (Appeal) в БД используя RestTemplate
 */
@RestController
@Log4j2
@AllArgsConstructor
@Tag(name= "Обращения", description = "Методы для работы с обращениями")
@RequestMapping("/api/edo/allAppeals")
public class AllAppealController {
    private final AllAppealService allAppealService;



    @Operation(summary = "Отправляет запрос на получение обращения в edo-service", description = "Обращение должно существовать")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AllAppealDto>> getAllAppealsForEmployee(EmployeeDto employeeDto, @RequestParam("start") int start, @RequestParam("end") int end) {
        Long creatorId = EmployeeBuilder.getCurrentEmployee().getId();
        log.info("Отправить get-запрос в service");
        var getAll = allAppealService.getAllAppeals(creatorId, start, end);
        log.info(" get-запрос отправлен в service");
        return new ResponseEntity<>(getAll, HttpStatus.CREATED);
    }

}
