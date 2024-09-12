package com.ridango.game.config;

import com.ridango.game.constants.AppConstants;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    /**
     * Create a bean of type {@link RestTemplate} with <strong>AppConstants.COCKTAIL_DB_BASE_RUI</strong> as the base uri
     *
     * @return {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().rootUri(AppConstants.COCKTAIL_DB_BASE_RUI).build();
    }
}
