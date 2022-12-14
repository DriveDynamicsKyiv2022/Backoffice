package com.griddynamics.backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {EmbeddedMongoAutoConfiguration.class,
MongoAutoConfiguration.class
})
public class BackofficeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackofficeServiceApplication.class, args);
    }

}
