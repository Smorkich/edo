package com.education.controller.currentUser;

import com.education.service.authentication.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static model.constant.Constant.CURRENT_USER_REST_URL;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping(CURRENT_USER_REST_URL)
@Tag(name = "Текущий пользователь", description = "Методы для нахождения текущего пользователя")
public class CurrentUserController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Поиск текущего пользователя")
    @GetMapping()
    public ResponseEntity<EmployeeDto> getCurrentUser() {
        log.info("Получаем текущего пользователя");
        var currentUser = authenticationService.getEmployeeByUsernameOrEmail();
        log.info("EmployeeDto успешно получен");
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }
}
