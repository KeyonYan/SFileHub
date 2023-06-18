package com.keyon.sfilehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SFileHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(SFileHubApplication.class, args);
    }

}
