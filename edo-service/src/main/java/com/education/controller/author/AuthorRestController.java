package com.education.controller.author;

import com.education.service.author.AuthorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AuthorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Рест-контроллер для Author
 */

@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Rest- контроллер для работы с авторами")
@RequestMapping("/api/service/author")
public class AuthorRestController {
    private final AuthorService authorService;

    @Operation(summary = "Получает всех авторов", description = "Автор должен существовать")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AuthorDto>> getAllAuthors() {
        log.info("Sent GET request to get all authors from the database");
        var authorDtoCollection = authorService.findAll();
        log.info("Response from database:{}", authorDtoCollection);
        return new ResponseEntity<>(authorDtoCollection, HttpStatus.OK);
    }

    /**
     * Метод передает нового автора в edo-repository для сохранения в таблицу author,
     * а также преобразует строку адреса автора в объект AddressDto с помощью geocode-maps.yandex
     * и передает этот объект в edo-repository для сохранения в таблицу address
     */
    @Operation(summary = "Добавляет автора", description = "Новый автор не должен существовать")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> addAuthorAction(@RequestBody AuthorDto authorDto) {
        log.info("Send POST request to add author to databases: {}", authorDto);
        authorService.save(authorDto);
        log.info("Author added to database");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Принимает запрос на создание Author'ов, которые передаются в теле HTTP запроса
     * <p>Вызывает метод saveAll() из интерфейса AuthorService, микросервиса edo-service
     *
     * @param authorDtos добавляемые AuthorDto
     * @return ResponseEntity<Collection < AuthorDto> - ResponseEntity коллекции DTO сущности AuthorDto (авторы обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Создает авторов в БД")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AuthorDto>> saveAll(@RequestBody Collection<AuthorDto> authorDtos) {
        log.info("Send a post-request to edo-repository to post new Authors to database");
        var saved = authorService.saveAll(authorDtos);
        log.info("Response: {} was added to database", saved);
        return new ResponseEntity<>(authorDtos, HttpStatus.CREATED);
    }

    @Operation(summary = "Удаление автора", description = "Автор должен существовать")
    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable Long id) {
        log.info("Send DELETE request to delete author with id={} to databases", id);
        authorService.delete(id);
        log.info("Author was deleted from the database");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Получание автора по id", description = "Автор должен существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        log.info("Sent GET request to get author with id={} from the database", id);
        var authorDto = authorService.findById(id);
        log.info("Response from database:{}", authorDto);
        return new ResponseEntity<>(authorService.findById(id), HttpStatus.OK);
    }
}
