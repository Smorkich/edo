package com.education.feign;

import feign.Logger;
import me.bvn13.openfeign.logger.normalized.NormalizedFeignLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFeignConfig {

    @Bean
    public Logger logger() {
        return new NormalizedFeignLogger();
    }

}
