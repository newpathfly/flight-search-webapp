package com.newpathfly.flight.search.webapp.config;

import com.newpathfly.api.ShoppingApi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfiguration {
    
    @Bean
    ShoppingApi shoppingApi() {
        return new ShoppingApi();
    }
}
