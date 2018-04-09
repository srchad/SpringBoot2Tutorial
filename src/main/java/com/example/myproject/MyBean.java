package com.example.myproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class MyBean implements CommandLineRunner{

    @Override
    public void run(String... args) throws Exception {
        System.out.print("[secret : {}]");
    }
}
