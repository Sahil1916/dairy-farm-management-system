package com.sahil.dairy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class MyDairySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyDairySystemApplication.class, args);

        System.out.println("project dairy");
    }
}