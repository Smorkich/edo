package com.education.controller.appeal;

import com.education.exception_handling.AppealAccessDeniedException;
import com.education.exception_handling.AppealCustomException;
import com.education.exception_handling.AppealIncorrectData;
import com.education.publisher.appeal.AppealPublisher;
import com.education.service.appeal.AppealService;
import com.education.util.Validator;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AppealDto;
import model.dto.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/service/appeal")
public class AppealRestController {

    private AppealService appealService;

    private AppealPublisher appealPublisher;

    @ApiOperation(value = "В строке таблицы Appeal заполняет поле archivedDate", notes = "Строка в Appeal должна существовать")
    @PutMapping(value = "/move/{id}")
    public ResponseEntity<AppealDto> moveToArchive(@PathVariable Long id) {
        appealService.moveToArchive(id);
        log.info("Moving appeal with id: {} to edo-repository is success!", id);
        return new ResponseEntity<>(appealService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Находит все строки таблицы Appeal с полем acrhivedDate = null", notes = "Строка в Appeal должна существовать")
    @GetMapping(value = "/findAllNotArchived", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AppealDto>> findAllNotArchived() {
        log.info("Getting from database all appeals with field acrhivedDate = null");
        var appealDtoCollection = appealService.findAllNotArchived();
        log.info("Appeals: {}", appealDtoCollection);
        return new ResponseEntity<>(appealDtoCollection, HttpStatus.OK);
    }

    @ApiOperation(value = "Находит строку таблицы Appeal c полем acrhivedDate = null, по заданному id", notes = "Строка в Appeal должна существовать")
    @GetMapping(value = "/findByIdNotArchived/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> findByIdNotArchived(@PathVariable Long id) {
        log.info("Getting from database appeal with field acrhivedDate = null, with id: {}", id);
        var mockEmployee = getMockEmployee();
        var appealDto = appealService.findByIdNotArchived(id);
        Validator.validateAccess(mockEmployee, appealDto);
        log.info("Response from database: {}", appealDto);
        return new ResponseEntity<>(appealDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Находит все строки таблицы Appeal", notes = "Строка в Appeal должна существовать")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AppealDto>> getAllAppeal() {
        log.info("Getting from database all appeals");
        var appealDtoCollection = appealService.findAll();
        log.info("Response from database: {}", appealDtoCollection);
        return new ResponseEntity<>(appealDtoCollection, HttpStatus.OK);
    }

    @ApiOperation(value = "Удаляет строку таблицы Appeal по id", notes = "Строка в Appeal должна существовать")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> deleteAppeal(@PathVariable(value = "id") Long id) {
        log.info("Deleting from database appeal with id: {}", id);
        appealService.delete(id);
        log.info("Deleting from database appeal with id: {}, success!", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @ApiOperation(value = "Принимает обращение, отправляет на edo-repository", notes = "Обращение должен существовать")
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

    @ApiOperation(value = "Находит строку таблицы Appeal по id", notes = "Строка в Appeal должна существовать")
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
