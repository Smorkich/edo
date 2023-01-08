package com.education.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class SheduledService {
    private final ServiceExternalEmployeeImp serviceExternalEmployeeImp;
    @Scheduled(cron = "${cron.employee}")
    public void save() {
        serviceExternalEmployeeImp.save();
    }

}
