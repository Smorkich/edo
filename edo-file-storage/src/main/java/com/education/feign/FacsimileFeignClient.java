package com.education.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static model.constant.Constant.*;

@FeignClient(name = EDO_SERVICE_NAME, url = FACSIMILE_URL)
public interface FacsimileFeignClient {

    @GetMapping("/{id}/download")
    ResponseEntity<Resource> getFacsimileFileByEmployeeId(@PathVariable Long id);
}
