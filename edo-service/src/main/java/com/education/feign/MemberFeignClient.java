package com.education.feign;

import jakarta.validation.Valid;
import model.dto.MemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.MEMBER_URL;

@FeignClient(name = EDO_REPOSITORY_NAME)
public interface MemberFeignClient {

    @PostMapping(MEMBER_URL)
    ResponseEntity<MemberDto> save(@RequestBody @Valid MemberDto memberDto);

    @GetMapping(MEMBER_URL + "/{id}")
    ResponseEntity<MemberDto> findById(@PathVariable Long id);

    @DeleteMapping(MEMBER_URL + "/{id}")
    ResponseEntity<MemberDto> delete(@PathVariable Long id);

    @GetMapping(MEMBER_URL)
    ResponseEntity<Collection<MemberDto>> findAll();

    @GetMapping(MEMBER_URL + "/all/{ids}")
    Collection<MemberDto> findAllById(@PathVariable Iterable<Long> ids);
}