package com.education.controller.filePool;

import com.education.service.filePool.FilePoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FilePoolDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@Log4j2
@RestController
@AllArgsConstructor
@Tag(name = "Rest- контроллер для работы с файлами")
@RequestMapping("/api/service/filePool")
public class FilePoolController {

    private final FilePoolService filePoolService;

    @Operation(summary = "Добавление файла", description = "Файл не должен существовать")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilePoolDto> save(@RequestBody FilePoolDto filePoolDto) {
        log.info("Send POST request to add file to databases: {}", filePoolDto);
        filePoolService.save(filePoolDto);
        log.info("file added to database");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Удаление файла", description = "Файл должен существовать")
    @DeleteMapping("/{id}")
    public ResponseEntity<FilePoolDto> delete(@PathVariable Long id) {
        log.info("Send DELETE request to delete file with id={} to databases", id);
        filePoolService.delete(id);
        log.info("file was deleted from the database");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Принимает запрос на создание FilePool'ов, которые передаются в теле HTTP запроса
     * <p>Вызывает метод saveAll() из интерфейса FilePoolService, микросервиса edo-service
     *
     * @param filePoolDtos добавляемые FilePoolDto
     * @return ResponseEntity<Collection < FilePoolDto> - ResponseEntity коллекции DTO сущности FilePool (файлпулы обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Создает информацию о файле в БД")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<FilePoolDto>> saveAll(@RequestBody Collection<FilePoolDto> filePoolDtos) {
        log.info("Send a post-request to edo-repository to post new FilePools to database");
        filePoolService.saveAll(filePoolDtos);
        log.info("Response: {} was added to database", filePoolDtos);
        return new ResponseEntity<>(filePoolDtos, HttpStatus.CREATED);
    }

    @Operation(summary = "Получение файла по id", description = "Файл должен существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilePoolDto> findById(@PathVariable("id") long id) {
        log.info("Send a get-request to get file with id = {} from edo-repository ", id);
        var filePoolDto = filePoolService.findById(id);
        log.info("Response from edo-repository: {}", filePoolDto.getName());
        return new ResponseEntity<>(filePoolDto, HttpStatus.OK);
    }

    @Operation(summary = "Получение всех файлов", description = "Файл должен существовать")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<FilePoolDto>> findAll() {
        log.info("Sent GET request to get all file from the database");
        var fileDto = filePoolService.findAll();
        log.info("Response from database:{}", fileDto);
        return new ResponseEntity<>(fileDto, HttpStatus.OK);
    }


    @Operation(summary = "Архивация файла с добавлением времени архивации\n")
    @PostMapping("/{id}")
    private String moveToArchive(@PathVariable(name = "id") Long id) {
        log.info("Start update a file");
        filePoolService.moveToArchive(id);
        log.info("Update a file");
        return "Delete " + id;
    }

    @Operation(summary = "Получение файла без архивирования по id")
    @GetMapping("/NotArchived/{id}")
    private ResponseEntity<FilePoolDto> findByIdNotArchived(@PathVariable(name = "id") Long id) {
        log.info("send a response with the file not archived of the assigned ID");
        return new ResponseEntity<>(filePoolService.findByIdNotArchived(id), HttpStatus.OK);
    }

    @Operation(summary = "Получение файлов без архивирования по присовенным ids")
    @GetMapping("/NotArchivedAll/{ids}")
    private ResponseEntity<Collection<FilePoolDto>> findAllByIdNotArchived(@PathVariable(name = "ids") String ids) {
        log.info("send a response with the files not archived of the assigned IDs");
        return new ResponseEntity<>(filePoolService.findAllByIdNotArchived(ids), HttpStatus.OK);
    }

    @Operation(summary = "Получение всех устаревших файлов (устаревшими считаются все файлы, чья дата загрузки равна или больше переданной)")
    @GetMapping("/oldFiles/{filePeriod}")
    public ResponseEntity<Collection<UUID>> findAllOldFiles(@PathVariable(name = "filePeriod") int filePeriod) {
        log.info("send a request to retrieve outdated files");
        return new ResponseEntity<>(filePoolService.findAllOldFiles(filePeriod), HttpStatus.OK);
    }

}
