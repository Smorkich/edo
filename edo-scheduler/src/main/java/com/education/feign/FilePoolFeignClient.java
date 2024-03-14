package com.education.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.UUID;

import static model.constant.Constant.EDO_SERVICE_NAME;
import static model.constant.Constant.FILEPOOL_SERVICE_URL;

@FeignClient(name = EDO_SERVICE_NAME, contextId = "FIlePoolFeignClient")
public interface FilePoolFeignClient {

    @GetMapping(FILEPOOL_SERVICE_URL + "/oldFiles/{filePeriod}")
    Collection<UUID> findAllOldFiles(@PathVariable int filePeriod);
}
