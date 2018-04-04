package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
@SpringBootApplication
public class CoreApplication {
	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CoreApplication.class);
        app.setApplicationContextClass(AnnotationConfigApplicationContext.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
	}
}
