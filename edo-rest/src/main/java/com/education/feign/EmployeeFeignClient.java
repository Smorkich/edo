package com.education.feign;


import model.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


import static model.constant.Constant.*;

@FeignClient(name = EDO_SERVICE_NAME)
public interface EmployeeFeignClient {

    @GetMapping(value = EMPLOYEE_URL + "/searchByEmailAndUsername")
    EmployeeDto findByEmailAndUsername(@RequestParam("email") String email,
                                       @RequestParam ("username") String username);


}
