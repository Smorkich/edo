package com.education.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static model.constant.Constant.*;

@FeignClient(name = EDO_FILE_STORAGE_NAME, contextId = "AppealFileFeignClient")
public interface AppealFileFeignClient {

    @GetMapping(FILE_STORAGE_APPEAL_FILE_URL + "/xlsx/{appealId}")
    ResponseEntity<byte[]> downloadAppealResolutionsFileXLSX(@PathVariable Long appealId);
}
