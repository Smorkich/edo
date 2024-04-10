package com.education.feign;

import model.dto.AppealDto;
import jakarta.validation.Valid;
import model.dto.AddressDto;
import model.dto.AppealFileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Collection;

import java.util.Collection;

import static model.constant.Constant.*;

@FeignClient(name = EDO_REPOSITORY_NAME, contextId = "AppealFeignClient")
public interface AppealFeignClient extends BaseFeignClient<AppealDto> {

    @PutMapping(REPOSITORY_APPEAL_URL + "/move/{id}")
    void moveToArchive(@PathVariable("id") Long id);

    @GetMapping(REPOSITORY_APPEAL_URL + "/findByIdNotArchived/{id}")
    AppealDto findByIdNotArchived(@PathVariable("id") Long id);

    @GetMapping(REPOSITORY_APPEAL_URL + "/findByIdNotArchived")
    Collection<AppealDto> findAllNotArchived();

    @GetMapping(REPOSITORY_APPEAL_URL + "/findAppealByQuestionsId/{id}")
    AppealDto findAppealByQuestionsId(@PathVariable("id") Long id);

    @PostMapping(REPOSITORY_APPEAL_URL + "/register")
    AppealDto register(Long id);

    @PatchMapping(REPOSITORY_APPEAL_URL + "/updateAppealStatusWhereExecutionStatusIsPerformed/{resolutionId}")
    Void updateAppealStatusWhereExecutionStatusIsPerformed(@PathVariable Long resolutionId);

    @PatchMapping(REPOSITORY_APPEAL_URL + "/setAppealStatusIfLastResolutionArchived/{resolutionId}")
    ResponseEntity<Void> setAppealStatusIfLastResolutionArchived(@PathVariable Long resolutionId);

    @GetMapping(APPEAL_URL + "/download/xlsx/{appealId}")
    Collection<AppealFileDto> findAllByAppealIdForXLSX(@PathVariable Long appealId);
}
