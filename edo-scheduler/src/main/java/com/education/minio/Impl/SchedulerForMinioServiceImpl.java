package com.education.minio.Impl;

import com.education.minio.SchedulerForMinioService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AppealDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class SchedulerForMinioServiceImpl implements SchedulerForMinioService {

    private static final String URLForEdoFileStorage = "http://edo-file-storage/api/filestorage/minio";
    private static final String URLForEdoRepository = "http://edo-repository/api/repository/appeal";
    private RestTemplate restTemplate;

    @Scheduled(cron = ("${minio.cron}"))
    public void delete() {
        List<AppealDto> listAppeals = getAppeal();
        for (AppealDto aDTO : listAppeals) {
            if (!(aDTO.getArchivedDate() == null)) {
                restTemplate.getForObject(URLForEdoFileStorage + "/delete/" + aDTO.getNumber(), Void.class);
            }
        }
    }

    private List<AppealDto> getAppeal() {
        return restTemplate.getForObject(URLForEdoRepository, List.class);
    }


}
