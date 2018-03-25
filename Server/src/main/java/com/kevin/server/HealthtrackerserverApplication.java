package com.kevin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = {"com.kevin.datamodel"})
public class HealthtrackerserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthtrackerserverApplication.class, args);
    }
}
