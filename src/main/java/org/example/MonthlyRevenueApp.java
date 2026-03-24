package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Monthly Revenue Spring Boot application.
 *
 * This application provides REST APIs to:
 * - Create and retrieve orders
 * - Fetch orders by month
 * - Calculate monthly revenue with discount logic
 */
@SpringBootApplication
public class MonthlyRevenueApp {

    /**
     * Main method to bootstrap the Spring Boot application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MonthlyRevenueApp.class, args);
    }
}