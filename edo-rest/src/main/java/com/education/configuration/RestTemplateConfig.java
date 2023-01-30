package com.education.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

    /**
     * @author Andrey Kryukov & Aleksandr Kostenko
     *  RestTemplateConfig  создание bean RestTemplate и его конфигурация
     */
    @Configuration
    public class RestTemplateConfig {

        @Bean
        RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }

