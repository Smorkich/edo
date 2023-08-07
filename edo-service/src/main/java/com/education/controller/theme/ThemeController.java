package com.education.controller.theme;

import com.education.service.theme.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.ThemeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author AlexeySpiridonov
 * Rest-контроллер отправляет запрос от клиента в бд
 */
@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Rest- контроллер для работы с темами обращения")
@RequestMapping("/api/service/theme")

public class ThemeController {

    private final ThemeService themeService;

    @Operation(summary = "Создает тему обращения", description = "Тема обращения должна существовать")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThemeDto> save(@RequestBody ThemeDto themeDto) {
        log.info("Starting the save operation");
        themeService.save(themeDto);
        log.info("POST request successful");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Получение темы по id", description = "Тема обращения должна существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThemeDto> findById(@PathVariable Long id) {
        log.info("Sent GET request to get author with id={} from the database", id);
        var themeDto = themeService.findById(id);
        log.info("Response from database:{}", themeDto);
        return new ResponseEntity<>(themeDto, HttpStatus.OK);
    }


    @Operation(summary = "Добавляет в тему дату архивации", description = "Тема должна существовать")
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> moveToArchive(@PathVariable(name = "id") Long id) {
        log.info("Starting the archiving theme");
        themeService.moveToArchived(id);
        log.info("Theme with id = {} has been moved to archive", id);
        return new ResponseEntity<>("The theme is archived", HttpStatus.OK);
    }


    @Operation(summary = "Предоставление темы без архивации по id")
    @GetMapping("/notArchived/{id}")
    private ResponseEntity<ThemeDto> findByIdNotArchived(@PathVariable(name = "id") Long id) {
        log.info("send a response with the theme not archived of the assigned ID");
        return new ResponseEntity<>(themeService.findByIdNotArchived(id), HttpStatus.OK);
    }

    @Operation(summary = "Предоставление тем без архивации по IDs")
    @GetMapping("/department/notArchivedAll/{ids}")
    private ResponseEntity<Collection<ThemeDto>> findAllByIdNotArchived(@PathVariable(name = "ids") String ids) {
        log.info("send a response with the departments not archived of the assigned IDs");
        return new ResponseEntity<>(themeService.findByAllIdNotArchived(ids), HttpStatus.OK);

    }
}
