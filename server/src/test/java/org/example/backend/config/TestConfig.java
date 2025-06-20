package org.example.backend.config;

import org.example.backend.service.TranslationService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(SecurityConfig.class)
public class TestConfig {

    @Bean
    public TranslationService translationService() {
        return Mockito.mock(TranslationService.class);
    }
}