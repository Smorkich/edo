package com.education.controller.filePool;

import com.education.entity.FilePool;
import com.education.service.filePool.FilePoolService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FilePoolDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(FILEPOOL_URL)
public class FilePoolController {

    private final FilePoolService filePoolService;

    @ApiOperation(value = "Создает файл", notes = "Файл должен существовать")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FilePoolDto save(@RequestBody @Valid FilePoolDto filePoolDto) {
        log.info("Send a post-request to post new Address to database");
        FilePool save = filePoolService.save(FILE_POOL_MAPPER.toEntity(filePoolDto));
        log.info("Response: {} was added to database", filePoolDto);
        return FILE_POOL_MAPPER.toDto(save);
    }

    @ApiOperation(value = "Удаляет файл", notes = "Файл должен существовать")
    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        log.info("DELETE: /api/repository/filePool/" + id);
        filePoolService.delete(id);
        log.info("DELETE request successful");
        return HttpStatus.ACCEPTED;
    }

    @ApiOperation(value = "Gets authors by id", notes = "Author must exist")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FilePoolDto findById(@PathVariable Long id) {
        log.info("Sent GET request to get author with id={} from the database", id);
        var filePoolDto = FILE_POOL_MAPPER.toDto(filePoolService.findById(id));
        log.info("Response from database:{}", filePoolDto);
        return filePoolDto;
    }


    @ApiOperation(value = "Gets file by uuid", notes = "File must exist")
    @GetMapping(value = "/info/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FilePoolDto findByUuid(@PathVariable UUID uuid) {
        log.info("Sent GET request to get file with uuid={} from the database", uuid);
        var filePoolDto = FILE_POOL_MAPPER.toDto(filePoolService.findByUuid(uuid));
        log.info("Response from database:{}", filePoolDto);
        return filePoolDto;
    }


    @ApiOperation(value = "Возвращает все файлы", notes = "Файлы должны существовать")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<FilePoolDto> findAll() {
        log.info("Send a get-request to get all file from database");
        var filePoolDto = FILE_POOL_MAPPER.toDto(filePoolService.findAll());
        log.info("Response from database: {}", filePoolDto);
        return filePoolDto;
    }

    @ApiOperation(value = "Добавляет в файл архивную дату", notes = "Файл должен существовать")
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private String moveToArchive(@PathVariable(name = "id") Long id) {
        filePoolService.moveToArchive(id);
        return "The file is archived";
    }

    @ApiOperation(value = "Предоставление файла без архивации")
    @GetMapping("/noArchived/{id}")
    private FilePoolDto getFileNotArchived(@PathVariable Long id) {
        return FILE_POOL_MAPPER.toDto(filePoolService.findByIdAndArchivedDateNull(id));
    }

    @ApiOperation(value = "Предоставление файлов без архивации")
    @GetMapping("/noArchived/{ids}")
    private Collection<FilePoolDto> getFilesNotArchived(@PathVariable List<Long> ids) {
        return FILE_POOL_MAPPER.toDto(filePoolService.findByIdInAndArchivedDateNull(ids));
    }

}
