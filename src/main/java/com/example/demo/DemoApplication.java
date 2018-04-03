package com.example.demo;

import com.example.demo.listener.ApplicationEventListener;
import org.springframework.boot.Banner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DemoApplication {

//    @Bean
//    public ExitCodeGenerator exitCodeGenerator() {
//        return () -> 2;
//    }
//
//    public static void main(String[] args) {
//        System.exit(SpringApplication
//                .exit(SpringApplication.run(DemoApplication.class, args)));
//    }

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(DemoApplication.class)
                .listeners(new ApplicationEventListener())
                .run(args);

    }
}
