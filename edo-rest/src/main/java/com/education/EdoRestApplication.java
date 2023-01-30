package com.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EdoRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(EdoRestApplication.class, args);
    }
}