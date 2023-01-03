package com.griddynamics.backoffice.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@Profile("local")
public class MongoDaoConfig {

    @Bean
    public MongoTemplate mongoTemplate(DbCredentials dbCredentials) {
        return new MongoTemplate(mongoClient(dbCredentials), dbCredentials.getDbName());
    }

    private MongoClient mongoClient(DbCredentials dbCredentials) {
        return MongoClients.create(mongoClientSettings(dbCredentials));
    }

    private MongoClientSettings mongoClientSettings(DbCredentials credentials) {
        ConnectionString connection = new ConnectionString(credentials.getUri());
        return MongoClientSettings.builder().applyConnectionString(connection).build();
    }
}
