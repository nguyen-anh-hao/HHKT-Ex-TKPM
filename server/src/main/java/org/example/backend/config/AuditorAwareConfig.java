package org.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import java.util.Optional;

@Configuration
public class AuditorAwareConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("admin"); // Replace with real user from security context
    }
}

