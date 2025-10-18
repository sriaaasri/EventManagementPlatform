package com.hanuman.event.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditorAware")
public class JpaConfiguration {

    @Bean
    AuditorAware<String> getAuditorAware(){
        return new AuditorAwareImpl();
    }

}
