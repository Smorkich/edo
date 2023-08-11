package com.education.feign;
import jakarta.validation.Valid;
import model.dto.ThemeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.THEME_URL;


@FeignClient(name = EDO_REPOSITORY_NAME)
public interface ThemeFeignClient {

    @GetMapping(THEME_URL + "{/ids}")
    Collection<ThemeDto> findByAllId(@PathVariable String ids);


    @GetMapping(THEME_URL + "{/id}")
    ThemeDto findById(@PathVariable Long id);

    @PostMapping(THEME_URL)
    ThemeDto save(@RequestBody @Valid ThemeDto notificationDto);

    @PostMapping(THEME_URL + "/{id}")
    String moveToArchive(@PathVariable(name = "id") Long id);

    @GetMapping(THEME_URL + "/noArchived/{id}")
    ThemeDto findByIdNotArchived(@PathVariable Long id);

    @GetMapping(THEME_URL + "/notArchivedAll/{ids}")
    Collection<ThemeDto> findByAllIdNotArchived(@PathVariable(name = "ids") String ids);
}

