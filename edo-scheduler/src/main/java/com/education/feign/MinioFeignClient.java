package com.education.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static model.constant.Constant.EDO_SERVICE_NAME;
import static model.constant.Constant.SERVICE_MINIO_URL;

@FeignClient(name = "MinioFeignClient")
public interface MinioFeignClient {

    @DeleteMapping(SERVICE_MINIO_URL + "/delete/{fileName}")
    ResponseEntity<String> delete(@PathVariable String fileName);
}
