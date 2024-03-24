package com.education.service.authentication.impl;

import com.education.feign.EmployeeFeignClient;
import com.education.service.authentication.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final EmployeeFeignClient employeeFeignClient;


    @Override
    public EmployeeDto getEmployeeByUsernameAndEmail() {
        JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> attributes = new HashMap<>();

        if (token == null) {

            attributes.put("preferred_username", "test_username");
            attributes.put("email", "test@mail.ru");
        } else {

            attributes = token.getTokenAttributes();
        }

        String username = (String) attributes.get("preferred_username");
        String email = (String) attributes.get("email");


        return employeeFeignClient.findByEmailAndUsername(email, username);
    }
}
