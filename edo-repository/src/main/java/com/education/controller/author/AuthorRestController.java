package com.education.controller.author;

import com.education.service.author.AuthorService;
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

import static com.education.mapper.AuthorMapper.AUTHOR_MAPPER;

/**
 * Рест-контроллер для Author
 */

@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Rest- контроллер для работы с авторами")
@RequestMapping("/api/repository/author")
public class AuthorRestController {

    private final AuthorService authorService;

    @Operation(summary = "Получает всех авторов", description = "Автор должен существовать")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AuthorDto>> getAllAuthors() {
        log.info("Sent GET request to get all authors from the database");
        var authorDtoCollection = AUTHOR_MAPPER.toDto(authorService.findAll());
        log.info("Response from database:{}", authorDtoCollection);
        return new ResponseEntity<>(authorDtoCollection, HttpStatus.OK);
    }

    @Operation(summary = "Добавляет автора", description = "Новый автор не должен существовать")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> addAuthorAction(@RequestBody AuthorDto authorDto) {
        log.info("Send POST request to add author to databases: {}", authorDto);
        var saved = authorService.save(AUTHOR_MAPPER.toEntity(authorDto));
        log.info("Author added to database");
        return new ResponseEntity<>(AUTHOR_MAPPER.toDto(saved), HttpStatus.CREATED);
    }

    /**
     * Принимает запрос на создание авторов в БД, которые передаются в теле HTTP запроса
     * <p>Вызывает метод saveAll() из интерфейса AuthorService, микросервиса edo-repository
     *
     * @param authorDtos добавляемые AuthorDto
     * @return ResponseEntity<Collection < AuthorDto> - ResponseEntity коллекции DTO сущности Author (авторы обращения)
     * @apiNote HTTP Method - POST
     */
    @ApiOperation(value = "Создает авторов в БД")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AuthorDto>> saveAll(@RequestBody Collection<AuthorDto> authorDtos) {
        log.info("Send a query to repository to post new Authors to database");
        var savedAll = authorService.saveAll(AUTHOR_MAPPER.toEntity(authorDtos));
        log.info("Response: {} was added to database", authorDtos);
        return new ResponseEntity<>(AUTHOR_MAPPER.toDto(savedAll), HttpStatus.CREATED);
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
        var authorDto = AUTHOR_MAPPER.toDto(authorService.findById(id));
        log.info("Response from database:{}", authorDto);
        return new ResponseEntity<>(authorDto, HttpStatus.OK);
    }
}
