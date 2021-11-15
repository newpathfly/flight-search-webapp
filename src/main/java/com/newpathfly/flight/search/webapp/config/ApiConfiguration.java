package com.newpathfly.flight.search.webapp.config;

import java.net.MalformedURLException;
import java.net.URL;

import com.newpathfly.api.ShoppingApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ApiConfiguration {

    @Value("#{environment.NEWPATHFLY_API_BASE_URL}")
    private String _baseUrl;

    @Bean
    ShoppingApi shoppingApi() throws MalformedURLException {
        ShoppingApi shoppingApi = new ShoppingApi();

        if (_baseUrl != null) {
            new URL(_baseUrl);
            shoppingApi.getApiClient().setBasePath(_baseUrl);
        }

        log.info(String.format("API base URL: %s", shoppingApi.getApiClient().getBasePath()));

        return shoppingApi;
    }
}
