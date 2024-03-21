package com.education.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.REPOSITORY_APPEAL_URL;

@FeignClient(name = EDO_REPOSITORY_NAME, contextId = "AppealFeignClient")
public interface AppealFeignClient {

    @GetMapping(REPOSITORY_APPEAL_URL + "/isAllResolutionsOver/{resolutionId}")
    Long isLastAppealResolutionArchived(@PathVariable Long resolutionId);

    @PatchMapping(REPOSITORY_APPEAL_URL + "/setAppealStatusIfLastResolutionArchived/{id}")
    ResponseEntity<Void> setAppealStatusIfLastResolutionArchived(@PathVariable Long id);
}
