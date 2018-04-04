package com.example.myproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MyBean implements CommandLineRunner{

    @ConfigurationProperties(prefix = "another")
    @Bean
    public AnotherComponent anotherComponent() {
        /// 24.7.2
        return null;
    }

    @Override
    public void run(String... args) throws Exception {
        // Do something. // 23.8
    }
}
