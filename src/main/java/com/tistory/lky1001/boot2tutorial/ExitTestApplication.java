package com.tistory.lky1001.boot2tutorial;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExitTestApplication {

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 42;
    }

    public static void main(String[] args) {
        // Process finished with exit code 42
        System.exit(SpringApplication
                .exit(SpringApplication.run(ExitTestApplication.class, args)));
    }
}
