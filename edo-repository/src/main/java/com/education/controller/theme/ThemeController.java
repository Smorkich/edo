package com.education.controller.theme;

import com.education.entity.Theme;
import com.education.service.theme.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.ThemeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static com.education.mapper.ThemeMapper.THEME_MAPPER;

/**
 * @author AlexeySpiridonov
 * Rest-контроллер отправляет запрос от клиента в бд
 */
@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Rest- контроллер для работы с темами обращения")
@RequestMapping("/api/repository/theme")
public class ThemeController {

    private final ThemeService themeService;

    @Operation(summary = "Создает тему обращения", description = "Тема обращения должна существовать")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<Theme>  save(@RequestBody ThemeDto themeDto) {
    public ThemeDto save(@RequestBody @Valid ThemeDto notificationDto) {
        log.info("Starting the save operation");
        themeService.save(THEME_MAPPER.toEntity(notificationDto));
        log.info("POST request successful");
        return notificationDto;
    }

    @Operation(summary = "Удаляет тему обращения", description = "Тема обращения должна существовать")
    @DeleteMapping("/{id}")
    public ResponseEntity<ThemeDto> delete(@PathVariable Long id) {
        log.info("DELETE: /api/repository/filePool/" + id);
        themeService.delete(id);
        log.info("DELETE request successful");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @Operation(summary = "Получение темы по id", description = "Тема обращения должна существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ThemeDto findById(@PathVariable Long id) {
        log.info("Sent GET request to get author with id={} from the database", id);
        ThemeDto themeDto = THEME_MAPPER.toDto(themeService.findById(id));
        log.info("Response from database:{}", themeDto);
        return themeDto;
    }

    @Operation(summary = "Получение всех тем")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ThemeDto>> findAll() {
        log.info("Sent GET request to get all authors from the database");
        var themeDtoCollection = THEME_MAPPER.toDto(themeService.findAll());
        log.info("Response from database:{}");
        return new ResponseEntity<>(themeDtoCollection, HttpStatus.OK);
    }

    @Operation(summary = "Добавляет в тему дату архивации", description = "Тема должна существовать")
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private String moveToArchive(@PathVariable(name = "id") Long id) {
        log.info("Starting the archiving theme");
        themeService.moveToArchive(id);
        log.info("Archiving a theme");
        return "moveToArchive";
    }

    @Operation(summary = "Предоставление темы без архивации по id")
    @GetMapping("/noArchived/{id}")
    private ThemeDto getThemeNotArchived(@PathVariable Long id) {
        return THEME_MAPPER.toDto(themeService.findByIdAndArchivedDateNull(id));
    }

    @Operation(summary = "Предоставление тем без архивации по IDs")
    @GetMapping("/noArchived/{ids}")
    private  ResponseEntity<Collection<ThemeDto>> getThemesNotArchived(@PathVariable List<Long> ids) {
        Collection<ThemeDto> themeDto = THEME_MAPPER.toDto(themeService.findByIdInAndArchivedDateNull(ids));
        return  new ResponseEntity<>(themeDto,HttpStatus.OK);
    }

}
