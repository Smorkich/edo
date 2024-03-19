package com.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * @author Usolkin Dmitry
 * SchedulerApplication  создание bean ObjectMapper и его конфигурация
 */
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class EdoSchedulerApplication {
    public static void main(String[] args) {

        SpringApplication.run(EdoSchedulerApplication.class, args);
    }

}
