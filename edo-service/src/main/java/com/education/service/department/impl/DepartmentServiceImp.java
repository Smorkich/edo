package com.education.service.department.impl;


import com.education.feign.DepartmentFeignClient;
import com.education.service.department.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.DepartmentDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;


/**
 * @author Usolkin Dmitry
 * Сервис, который соединяет реализацию двух модулей через RestTemplate
 * Имеет все те же операции, что и service в edo-repository
 */
@Service
@Log4j2
@AllArgsConstructor
public class DepartmentServiceImp implements DepartmentService {

    private final DepartmentFeignClient departmentFeignClient;


    @Override
    public void save(DepartmentDto department) {
        departmentFeignClient.save(department);
        log.info("sent a request to save the department in edo - repository");
    }

    @Override
    public void removeToArchived(Long id) {
        departmentFeignClient.removeToArchived(id);
        log.info("sent a request to archive the department in edo - repository");
    }

    @Override
    public DepartmentDto findById(Long id) {
        log.info("sent a request to receive the department in edo - repository");
        return departmentFeignClient.findById(id);
    }

    @Override
    public Collection<DepartmentDto> findByAllId(String ids) {
        log.info("sent a request to receive the departments in edo - repository");
        return departmentFeignClient.findByAllId(ids);
    }

    @Override
    public DepartmentDto findByIdNotArchived(Long id) {
        log.info("sent a request to receive the department not archived in edo - repository");
        return departmentFeignClient.findByIdNotArchived(id);

    }

    @Override
    public Collection<DepartmentDto> findByAllIdNotArchived(String ids) {
        log.info("sent a request to receive the departments not archived in edo - repository");
        return departmentFeignClient.findByAllIdNotArchived(ids);
    }


}
