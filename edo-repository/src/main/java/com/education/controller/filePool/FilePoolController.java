package com.education.controller.filePool;

import com.education.entity.FilePool;
import com.education.serivce.filePoll.FilePoolService;
import com.education.util.FilePoolUtil;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import model.dto.FilePoolDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Rest-контроллер отправляет запрос от клиента в бд
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/repository/filePool")
public class FilePoolController {

    private final FilePoolService filePoolService;
    Logger logger = Logger.getLogger(FilePoolController.class.getName());

    @ApiOperation(value = "Создает файл", notes = "Файл должен существовать")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilePoolDto> save(@RequestBody @Valid FilePoolDto filePoolDto) {
        logger.info("POST: /api/repository/filePool");
        filePoolService.save(FilePoolUtil.toFilePool(filePoolDto));
        logger.info("POST request successful");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Удаляет файл", notes = "Файл должен существовать")
    @DeleteMapping("/{id}")
    public ResponseEntity<FilePoolDto> delete(@PathVariable Long id) {
        logger.info("DELETE: /api/repository/filePool/" + id);
        filePoolService.delete(id);
        logger.info("DELETE request successful");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Находит файл по id", notes = "Файл должен существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilePoolDto> findById(@PathVariable Long id) {
        logger.info("Get: /api/repository/filePool/" + id);
        return new ResponseEntity<>(FilePoolUtil.toDto(filePoolService.findById(id)), HttpStatus.OK);
    }

    @ApiOperation(value = "Выводит id всех файлов", notes = "Файлы должны существовать")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<FilePoolDto>> findAll() {
        logger.info("Sent GET request to get all authors from the database");
        Collection<FilePoolDto> filePoolDtoCollection = FilePoolUtil.ListFilePooDto((List<FilePool>) filePoolService.findAll());
        logger.info("Response from database:{}");
        return new ResponseEntity<>(filePoolDtoCollection, HttpStatus.OK);
    }

    @ApiOperation(value = "Добавляет в файл архивную дату", notes = "Файл должен существовать")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilePoolDto> moveToArchive(@RequestBody @Valid FilePoolDto filePoolDto) {
        logger.info("POST: /api/repository/filePool");
        filePoolService.save(FilePoolUtil.toFilePool(filePoolDto));
        logger.info("POST request successful");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


//    Collection<FilePool> findAllById(Iterable<Long> ids);
//    FilePool findByIdNotArchived(Long id);
//    Collection<FilePool> findAllByIdNotArchived(Iterable<Long> ids);
}
