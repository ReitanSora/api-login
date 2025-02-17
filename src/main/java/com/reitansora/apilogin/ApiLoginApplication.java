package com.reitansora.apilogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the API Login application.
 * This class is responsible for bootstrapping the Spring Boot application.
 */
@SpringBootApplication
public class ApiLoginApplication {

    /**
     * The main method that serves as the entry point for the Spring Boot application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiLoginApplication.class, args);
    }

}
