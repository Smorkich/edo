package com.education.controller.appeal;

import com.education.exception_handling.AppealAccessDeniedException;
import com.education.exception_handling.AppealCustomException;
import com.education.exception_handling.AppealIncorrectData;
import com.education.publisher.appeal.AppealPublisher;
import com.education.service.appeal.AppealService;
import com.education.util.Validator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AppealDto;
import model.dto.EmployeeDto;
import model.dto.FilePoolDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Обращения", description = "Методы для работы с обращениями")
@RequestMapping("/api/service/appeal")
public class AppealRestController {

    private AppealService appealService;

    private AppealPublisher appealPublisher;

    @Operation(summary = "В строке таблицы Appeal заполняет поле archivedDate", description = "Строка в Appeal должна существовать")
    @PutMapping(value = "/move/{id}")
    public ResponseEntity<AppealDto> moveToArchive(@PathVariable Long id) {
        appealService.moveToArchive(id);
        log.info("Moving appeal with id: {} to edo-repository is success!", id);
        return new ResponseEntity<>(appealService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Находит все строки таблицы Appeal с полем acrhivedDate = null",
            description = "Строка в Appeal должна существовать")
    @GetMapping(value = "/findAllNotArchived", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AppealDto>> findAllNotArchived() {
        log.info("Getting from database all appeals with field acrhivedDate = null");
        var appealDtoCollection = appealService.findAllNotArchived();
        log.info("Appeals: {}", appealDtoCollection);
        return new ResponseEntity<>(appealDtoCollection, HttpStatus.OK);
    }

    @Operation(summary = "Находит строку таблицы Appeal c полем acrhivedDate = null, по заданному id",
            description = "Строка в Appeal должна существовать")
    @GetMapping(value = "/findByIdNotArchived/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> findByIdNotArchived(@PathVariable Long id) {
        log.info("Getting from database appeal with field acrhivedDate = null, with id: {}", id);
        var mockEmployee = getMockEmployee();
        var appealDto = appealService.findByIdNotArchived(id);
        Validator.validateAccess(mockEmployee, appealDto);
        log.info("Response from database: {}", appealDto);
        return new ResponseEntity<>(appealDto, HttpStatus.OK);
    }

    @Operation(summary = "Находит все строки таблицы Appeal", description = "Строка в Appeal должна существовать")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AppealDto>> getAllAppeal() {
        log.info("Getting from database all appeals");
        var appealDtoCollection = appealService.findAll();
        log.info("Response from database: {}", appealDtoCollection);
        return new ResponseEntity<>(appealDtoCollection, HttpStatus.OK);
    }

    @Operation(summary = "Удаляет строку таблицы Appeal по id", description = "Строка в Appeal должна существовать")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> deleteAppeal(@PathVariable(value = "id") Long id) {
        log.info("Deleting from database appeal with id: {}", id);
        appealService.delete(id);
        log.info("Deleting from database appeal with id: {}, success!", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Operation(summary = "Принимает обращение, отправляет на edo-repository", description = "Обращение должно существовать")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> save(@RequestBody AppealDto appealDto) {
        Validator.getValidateAppeal(appealDto);
        log.info("Send a post-request to edo-repository to post new Appeal to database");
        var save = appealService.save(appealDto);
        log.info("sending to edo-repository", save);
        log.info("Sending a message to employees");
        appealService.sendMessage(save);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @Operation(summary = "Находит строку таблицы Appeal по id", description = "Строка в Appeal должна существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> getAppealById(@PathVariable Long id) {
        var mockEmployee = getMockEmployee();
        log.info("Getting from database appeal with id: {}", id);
        var appealDto = appealService.findById(id);
        Validator.validateAccess(mockEmployee, appealDto);
        appealPublisher.employeeReadAppealMessage("Employee with id: ${mockEmployee.getId()} read the appeal with id: ${id}");
        appealPublisher.employeeReadAppealMessage(String.format("Employee with id: %d read the appeal with id: %d",
                mockEmployee.getId(), id));
        log.info("Response from database: {}", appealDto);
        return new ResponseEntity<>(appealDto, HttpStatus.OK);
    }


    @Operation(summary = "Добавляет файл к выбранному обращению")
    @PostMapping("/upload")
    public ResponseEntity<AppealDto> uploadFile(@RequestParam(value = "id", required = true) Long id , @RequestParam(value = "file", required = true) FilePoolDto file) {
        log.info("'Appeal/Upload' - сохранение/прикрепление файла за обращением");
        var saveFile =appealService.upload(id,file);
        log.info("'Appeal/Upload' - файл "+ saveFile.getId()+" прикреплён к обращению " + id);
        return new ResponseEntity<>(saveFile, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<String> validUserException(MethodArgumentNotValidException ex) {
        log.warn(ex.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.toString());
    }

    @ExceptionHandler
    public ResponseEntity<AppealIncorrectData> handleException(AppealCustomException exception) {
        AppealIncorrectData appealIncorrectData = new AppealIncorrectData();
        appealIncorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(appealIncorrectData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppealIncorrectData> handleException(AppealAccessDeniedException exception) {
        AppealIncorrectData appealIncorrectData = new AppealIncorrectData();
        appealIncorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(appealIncorrectData, HttpStatus.FORBIDDEN);
    }

    private EmployeeDto getMockEmployee() {
        return EmployeeDto.builder().id(4L).build();
    }
}
