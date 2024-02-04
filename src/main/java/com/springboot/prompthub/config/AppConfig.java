package com.springboot.prompthub.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.javafaker.Faker;
import com.springboot.prompthub.models.entity.User;
import com.springboot.prompthub.service.impl.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
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

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
        //objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        return objectMapper;
    }
}
