package org.example.backend.config;

import org.example.backend.common.TranslationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<TranslationFilter> translationFilterRegistration(
            TranslationFilter translationFilter) {
        FilterRegistrationBean<TranslationFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(translationFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}