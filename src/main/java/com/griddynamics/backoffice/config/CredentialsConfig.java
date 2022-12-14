package com.griddynamics.backoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class CredentialsConfig {

    @Bean
    @Profile("local")
    public DbCredentials dbCredentials() {
        return DbCredentials.of("mongodb://localhost:27017", "backoffice_db");
    }

}
