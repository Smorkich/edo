package com.education.controller.facsimile;

import com.education.service.facsimile.FacsimileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FacsimileDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/facsimile")
@Tag(name = "Контроллер для работы с FacsimileDto", description = "Метод принимает FacsimileDto, отправляет на edo-service")
public class FacsimileController {

    private FacsimileService facsimileService;

    @Operation(summary = "Принимает FacsimileDto, отправляет на edo-service")
    @PostMapping(value = "/save")
    public ResponseEntity<FacsimileDto> save(@RequestBody FacsimileDto facsimileDto){
        log.info("Отправка пост-запроса в edo-service");
        var save = facsimileService.save(facsimileDto);
        log.info("Пост-запрос отправлен!");
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }
}
