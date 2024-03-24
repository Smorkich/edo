package com.education.controller.authentication;

import com.education.service.authentication.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@RequestMapping("/api/rest/authentication")
@AllArgsConstructor
@Tag(name = "Аутентификация", description = "Метод для аутентификации сотрудников")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Предоставление сотрудника по email и username")
    @GetMapping(value = "/searchByEmailAndUsername", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto getEmployeeByEmailAndUsername() {
        return authenticationService.getEmployeeByUsernameAndEmail();
    }
}
