package com.example.securityApp.SpringSecurity.config;

import com.example.securityApp.SpringSecurity.auth.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditorAware")
public class AppConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public AuditorAware<String> getAuditorAware() {
        return new AuditorAwareImpl();
    }

    @Bean
    public PasswordEncoder hashPassword(){
        return new BCryptPasswordEncoder();
    }
}
