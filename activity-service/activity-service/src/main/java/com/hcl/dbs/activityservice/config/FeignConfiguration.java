package com.hcl.dbs.activityservice.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    Logger.Level feiginLoggerLevel(){
        return Logger.Level.HEADERS;
    }
}
