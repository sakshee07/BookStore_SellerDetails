package com.example.sellerdetails_bookstore_project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class SellerDetailsBookStoreProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellerDetailsBookStoreProjectApplication.class, args);
        System.out.println("--------------------------------");
        log.info("\nHello! Welcome to Book Store Seller Project!");
    }
}
