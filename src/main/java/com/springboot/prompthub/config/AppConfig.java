package com.springboot.prompthub.config;

import com.github.javafaker.Faker;
import com.springboot.prompthub.entity.User;
import com.springboot.prompthub.service.impl.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Locale;

@Configuration
@EnableJpaAuditing
public class AppConfig {

    @Bean
    public AuditorAware<User> auditorProvider() {
        return new AuditorAwareImpl();
    }

    @Bean
    Faker faker(){
        return new Faker(new Locale("pl"));
    }
}
