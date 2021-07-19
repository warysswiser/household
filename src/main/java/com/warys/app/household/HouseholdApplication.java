package com.warys.app.household;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class HouseholdApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseholdApplication.class, args);
    }

}
