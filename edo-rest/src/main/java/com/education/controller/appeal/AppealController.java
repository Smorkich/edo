package com.education.controller.appeal;

import com.education.service.address.AppealService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AppealDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest-контроллер в "edo-rest", служит для отправки запросов
 * от клиента(edo-rest) к другому микросервису(edo-repository) используя RestTemplate
 * и для получения данных в ответ.
 * "@Log4j2" нужна для создания логов, для удобной отладки программы
 */
@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api/edo/appeal")
public class AppealController {
    AppealService appealService;

    @ApiOperation(value = "Создает обращение в БД", notes = "Обращение должен существовать")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> save(@RequestBody AppealDto appealDto) {
        log.info("Send a post-request to edo-repository to post new Appeal to database" +
                " (RestTemplate on edo-service side)");
        appealService.save(appealDto);
        log.info("Response: {} was added to database", appealDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
