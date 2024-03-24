package com.education.service.authentication;

import model.dto.EmployeeDto;

public interface AuthenticationService {

    EmployeeDto getEmployeeByUsernameAndEmail();
}
