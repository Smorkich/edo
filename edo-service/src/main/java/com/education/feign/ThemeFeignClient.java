package com.education.feign;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * @author AlexeySpiridonov
 */
public class ThemeFeignClient {
    @Component
    public class FilePoolFeignClient {
        @LoadBalanced
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
}
