package com.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class EdoIntegrationApplication {
    public static void main(String[] args) {
        SpringApplication.run(EdoIntegrationApplication.class, args);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        SendEmail bean = context.getBean(SendEmail.class);
        bean.sendMail();
    }
}