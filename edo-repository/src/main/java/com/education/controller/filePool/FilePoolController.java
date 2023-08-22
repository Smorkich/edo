package com.education.controller.filePool;

import com.education.entity.FilePool;
import com.education.service.filePool.FilePoolService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FilePoolDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.education.mapper.FilePoolMapper.FILE_POOL_MAPPER;
import static model.constant.Constant.FILEPOOL_URL;

/**
 * @author Nadezhda Pupina
 * Rest-контроллер отправляет запрос от клиента в бд
 */
@Log4j2
@RestController
@AllArgsConstructor
@Tag(name = "Rest- контроллер для работы с файлами")
@RequestMapping(FILEPOOL_URL)
public class FilePoolController {

    private final FilePoolService filePoolService;

    @Operation(summary = "Добавление файла", description = "Файл не должен существовать")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FilePoolDto save(@RequestBody @Valid FilePoolDto filePoolDto) {
        log.info("Send a post-request to post new Address to database");
        var saved = filePoolService.save(FILE_POOL_MAPPER.toEntity(filePoolDto));
        log.info("Response: {} was added to database", filePoolDto);
        return FILE_POOL_MAPPER.toDto(saved);
    }


    /**
     * Принимает запрос на создание информации о файлах в БД, которые передаются в теле HTTP запроса
     * <p>Вызывает метод saveAll() из интерфейса FilePoolService, микросервиса edo-repository
     *
     * @param filePoolDtos добавляемые FilePoolDto
     * @return ResponseEntity<Collection < FilePoolDto> - ResponseEntity коллекции DTO сущности FilePool (файлы обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Создает информацию о файлах в БД")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<FilePoolDto> saveAll(@RequestBody Collection<FilePoolDto> filePoolDtos) {
        log.info("Send a query to repository to post new FilePools to database");
        var savedAll = filePoolService.saveAll(FILE_POOL_MAPPER.toEntity(filePoolDtos));
        log.info("Response: {} was added to database", filePoolDtos);
        return FILE_POOL_MAPPER.toDto(savedAll);
    }

    @Operation(summary = "Удаление файла", description = "Файл должен существовать")
    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        log.info("DELETE: /api/repository/filePool/" + id);
        filePoolService.delete(id);
        log.info("DELETE request successful");
        return HttpStatus.ACCEPTED;
    }

    @Operation(summary = "Получает авторов по id", description = "Автор должен существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FilePoolDto findById(@PathVariable Long id) {
        log.info("Sent GET request to get author with id={} from the database", id);
        var filePoolDto = FILE_POOL_MAPPER.toDto(filePoolService.findById(id));
        log.info("Response from database:{}", filePoolDto);
        return filePoolDto;
    }

    @Operation(summary = "Получение файла по uuid", description = "Файл должен существовать")
    @GetMapping(value = "/info/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FilePoolDto findByUuid(@PathVariable UUID uuid) {
        log.info("Sent GET request to get file with uuid={} from the database", uuid);
        var filePoolDto = FILE_POOL_MAPPER.toDto(filePoolService.findByUuid(uuid));
        log.info("Response from database:{}", filePoolDto);
        return filePoolDto;
    }


    @Operation(summary = "Получение всех файлов", description = "Файл должен существовать")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<FilePoolDto> findAll() {
        log.info("Send a get-request to get all file from database");
        var filePoolDto = FILE_POOL_MAPPER.toDto(filePoolService.findAll());
        log.info("Response from database: {}", filePoolDto);
        return filePoolDto;
    }

    @Operation(summary = "Добавление в файл архивную дату", description = "Файл должен существовать")
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private String moveToArchive(@PathVariable(name = "id") Long id) {
        filePoolService.moveToArchive(id);
        return "The file is archived";
    }

    @Operation(summary = "Получение файла без архивации по id")
    @GetMapping("/noArchived/{id}")
    private FilePoolDto getFileNotArchived(@PathVariable Long id) {
        return FILE_POOL_MAPPER.toDto(filePoolService.findByIdAndArchivedDateNull(id));
    }

    @Operation(summary = "Получение файлов без архивирования по присовенным ids")
    @GetMapping("/noArchived/{ids}")
    private Collection<FilePoolDto> getFilesNotArchived(@PathVariable List<Long> ids) {
        return FILE_POOL_MAPPER.toDto(filePoolService.findByIdInAndArchivedDateNull(ids));
    }

}
