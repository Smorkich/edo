package com.education.feign.address;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * noFeignClient в "edo-service", является клиентом, который делает запросы
 * на микросервис "edo-repository" для получения данных используя при этом RestTemplate
 * Здесь мы назначаем new RestTemplate() бином
 */
@Component
public class AddressFeignClient {

    /**
     * Здесь мы назначаем new RestTemplate() бином
     * Аннотация @LoadBalanced нужна для обращения одного микросервиса к другому при помощи имен в application.yml. Именно
     * поэтому обращение в сервисе идет на соседний микросервис с прописанным в настройках именем edo-repository, а не в интернет.
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
