package com.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EdoFileStorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(EdoFileStorageApplication.class, args);
    }
}