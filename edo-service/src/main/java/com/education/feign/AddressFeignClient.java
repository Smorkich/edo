package com.education.feign;

import jakarta.validation.Valid;
import model.dto.AddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.ADDRESS_URL;

@FeignClient(name = EDO_REPOSITORY_NAME)
public interface AddressFeignClient {

    @PostMapping(ADDRESS_URL)
    ResponseEntity<AddressDto> save(@RequestBody @Valid AddressDto addressDto);

    @GetMapping(ADDRESS_URL + "/{id}")
    ResponseEntity<AddressDto> findById(@PathVariable Long id);

    @DeleteMapping(ADDRESS_URL + "/{id}")
    ResponseEntity<AddressDto> delete(@PathVariable Long id);

    @GetMapping(ADDRESS_URL)
    ResponseEntity<Collection<AddressDto>> findAll();
}
