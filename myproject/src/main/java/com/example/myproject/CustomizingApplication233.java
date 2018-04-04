package com.example.myproject;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomizingApplication233 {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CustomizingApplication233.class);
        app.setBannerMode( Banner.Mode.OFF);
        app.run(args);
    }
}
