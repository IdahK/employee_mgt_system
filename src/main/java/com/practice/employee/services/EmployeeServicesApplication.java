package com.practice.employee.services;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmployeeServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeServicesApplication.class, args);
    }

}
