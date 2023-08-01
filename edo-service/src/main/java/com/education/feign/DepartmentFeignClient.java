package com.education.feign;

import com.education.config.FeignConfig;
import model.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

import static model.constant.Constant.DEPARTMENT_URL;

@FeignClient(name = "DepartmentFeignClient", configuration = FeignConfig.class)
public interface DepartmentFeignClient {

    @GetMapping(DEPARTMENT_URL + "/{id}")
    DepartmentDto findById(@PathVariable("id") Long id);

    @GetMapping(DEPARTMENT_URL + "/notArchived/{id}")
    DepartmentDto findByIdNotArchived(@PathVariable("id") Long id);

    @GetMapping(DEPARTMENT_URL + "/notArchivedAll/{ids}")
    Collection<DepartmentDto> findByAllIdNotArchived(@PathVariable("ids") String ids);

    @GetMapping(DEPARTMENT_URL + "/all/{ids}")
    Collection<DepartmentDto> findByAllId(@PathVariable("ids") String ids);

    @PostMapping(DEPARTMENT_URL)
    DepartmentDto save(@RequestBody DepartmentDto departmentDto);

    @PostMapping(DEPARTMENT_URL + "/{id}")
    DepartmentDto removeToArchived(@PathVariable("id") Long id);

}
