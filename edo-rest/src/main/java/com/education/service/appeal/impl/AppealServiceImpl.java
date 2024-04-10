package com.education.service.appeal.impl;

import com.education.feign.AppealFeignClient;
import com.education.service.appeal.AppealService;
import com.education.service.common.AbstractRestService;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import model.dto.AppealDto;
import model.dto.FilePoolDto;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
public class AppealServiceImpl extends AbstractRestService<AppealDto> implements AppealService {

    private final AppealFeignClient appealFeignClient;

    @Override
    public void moveToArchive(Long id) {
        AppealDto appeal = findById(id);
        if (appeal == null) {
            throw new NotFoundException("Appeal with ID " + id + " not found");
        }
        appeal.setArchivedDate(ZonedDateTime.now());
        appealFeignClient.moveToArchive(id);
    }

    @Override
    public AppealDto upload(Long id, FilePoolDto file) {
        return appealFeignClient.uploadFile(id, file).getBody();
    }

    /**
     * Отправляет запрос в edo-service на регистрацию Appeal по id, который передаётся в параметре
     * @param id идентификатор регистрируемого Appeal
     * @return AppealDto - DTO сущности Appeal (обращение)
     */
    @Override
    public AppealDto register(Long id) {
        return appealFeignClient.registerAppeal(id);
    }

}
