package com.education.feign;

import model.dto.AppealDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Collection;

import static model.constant.Constant.*;

@FeignClient(name = EDO_REPOSITORY_NAME, url = APPEAL_URL)
public interface AppealFeignClient extends BaseFeignClient<AppealDto> {
    @PutMapping("/move/{id}")
    void moveToArchive(@PathVariable("id") Long id);

    @GetMapping("/findByIdNotArchived/{id}")
    AppealDto findByIdNotArchived(@PathVariable("id") Long id);

    @GetMapping("/findByIdNotArchived")
    Collection<AppealDto> findAllNotArchived();

    @GetMapping("/findAppealByQuestionsId/{id}")
    AppealDto findAppealByQuestionsId(@PathVariable("id") Long id);

    @PostMapping("/register")
    AppealDto register(Long id);
}
