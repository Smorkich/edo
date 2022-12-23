package com.education.controller.filePool;

import com.education.entity.FilePool;
import com.education.serivce.filePoll.FilePoolService;
import com.education.util.FilePoolUtil;
import io.swagger.annotations.ApiOperation;
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

/**
 * @author Nadezhda Pupina
 * Rest-контроллер отправляет запрос от клиента в бд
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/repository/filePool")
public class FilePoolController {

    private final FilePoolService filePoolService;

    @ApiOperation(value = "Создает файл", notes = "Файл должен существовать")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String save(@RequestBody @Valid FilePool filePoolDto) {
        log.info("POST: /api/repository/filePool");
        //filePoolService.save(FilePoolUtil.toFilePool(filePoolDto));
        filePoolService.save(filePoolDto);
        //filePoolService.save(filePoolDto);
        log.info("POST request successful");
        //return new ResponseEntity<>(HttpStatus.CREATED);
        return "исполнено";
    }

    @ApiOperation(value = "Удаляет файл", notes = "Файл должен существовать")
    @DeleteMapping("/{id}")
    public ResponseEntity<FilePoolDto> delete(@PathVariable Long id) {
        log.info("DELETE: /api/repository/filePool/" + id);
        filePoolService.delete(id);
        log.info("DELETE request successful");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @ApiOperation(value = "Gets authors by id", notes = "Author must exist")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilePoolDto> findById(@PathVariable Long id) {
        log.info("Sent GET request to get author with id={} from the database", id);
        var filePoolDto = FilePoolUtil.toDto(filePoolService.findById(id));
        log.info("Response from database:{}", filePoolDto);
        return new ResponseEntity<>(filePoolDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Выводит id всех файлов", notes = "Файлы должны существовать")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<FilePoolDto>> findAll() {
        log.info("Sent GET request to get all authors from the database");
        Collection<FilePoolDto> filePoolDtoCollection = FilePoolUtil.ListFilePooDto((List<FilePool>) filePoolService.findAll());
        log.info("Response from database:{}");
        return new ResponseEntity<>(filePoolDtoCollection, HttpStatus.OK);
    }

    @ApiOperation(value = "Добавляет в файл архивную дату", notes = "Файл должен существовать")
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> moveToArchive(@PathVariable(name = "id") Long id) {
        filePoolService.moveToArchive(id);
        return new ResponseEntity<>("The file is archived", HttpStatus.OK);
    }

    @ApiOperation(value = "Предоставление файла без архивации")
    @GetMapping("/noArchived/{id}")
    private ResponseEntity<FilePoolDto> getFileNotArchived(@PathVariable Long id) {
        return new ResponseEntity<>(FilePoolUtil.toDto(filePoolService.findByIdAndArchivedDateNull(id)), HttpStatus.OK);
    }

    @ApiOperation(value = "Предоставление файлов без архивации")
    @GetMapping("/noArchived/{ids}")
    private  ResponseEntity<List<FilePoolDto>> getFilesNotArchived(@PathVariable List <Long> ids) {
        List<FilePoolDto> filePoolDto = filePoolService.findByIdInAndArchivedDateNull(ids)
                .stream().map(FilePoolUtil::toDto).toList();
        return  new ResponseEntity<>(filePoolDto,HttpStatus.OK);
    }

}
