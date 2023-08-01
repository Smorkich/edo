package com.education.feign;

import com.education.config.FeignConfig;
import model.dto.ApprovalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static model.constant.Constant.APPROVAL_URL;
import static model.constant.Constant.EDO_REPOSITORY_NAME;

@FeignClient(name = "ApprovalFeignClient", configuration = FeignConfig.class)
public interface ApprovalFeignClient {

    @PostMapping(APPROVAL_URL)
    ResponseEntity<ApprovalDto> save(@RequestBody ApprovalDto approvalDto);

    @GetMapping(APPROVAL_URL + "/{id}")
    ResponseEntity<ApprovalDto> findById(@PathVariable("id") long id);

    @GetMapping(APPROVAL_URL + "/all")
    ResponseEntity<Collection<ApprovalDto>> findAll();

    @GetMapping(APPROVAL_URL + "/all/{ids}")
    ResponseEntity<Collection<ApprovalDto>> findAllById(@PathVariable List<Long> ids);

    @DeleteMapping(APPROVAL_URL + "/{id}")
    ResponseEntity<Long> delete(@PathVariable Long id);

    @PatchMapping(APPROVAL_URL + "/move/{id}")
    ResponseEntity<ApprovalDto> moveToArchive(@PathVariable Long id);

    @GetMapping(APPROVAL_URL + "/findByIdNotArchived/{id}")
    ResponseEntity<ApprovalDto> findByIdNotArchived(@PathVariable Long id);

    @GetMapping(APPROVAL_URL + "/findAllNotArchived")
    ResponseEntity<Collection<ApprovalDto>> findAllNotArchived();

    @GetMapping(APPROVAL_URL + "/findByIdInAndArchivedDateNull/{ids}")
    ResponseEntity<Collection<ApprovalDto>> findByIdInAndArchivedDateNull(@PathVariable List<Long> ids);

    @PostMapping(APPROVAL_URL + "/update")
    ResponseEntity<ApprovalDto> update(@RequestBody ApprovalDto approvalDto);

    @PostMapping(APPROVAL_URL + "/send/{id}")
    ResponseEntity<ApprovalDto> sendForApproval(@PathVariable Long id);

}
