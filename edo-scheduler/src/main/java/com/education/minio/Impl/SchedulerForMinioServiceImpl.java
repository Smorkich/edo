package com.education.minio.Impl;
import com.education.minio.SchedulerForMinioService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
@Service
@Log4j2
@AllArgsConstructor
public class SchedulerForMinioServiceImpl implements SchedulerForMinioService {

    private RestTemplate restTemplate;

    @Scheduled(cron = "0 0 1 * * MON")
    public void delete() {
        log.info(LocalTime.now());
        restTemplate.getForObject("http://edo-file-storage/api/filestorage/minio/delete", Void.class);
        log.info(LocalTime.now());
    }
}
