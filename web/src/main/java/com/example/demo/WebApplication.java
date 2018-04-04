package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {

        new SpringApplicationBuilder()
                .sources(CoreApplication.class)
                .child(WebApplication.class)
                .bannerMode(Banner.Mode.LOG)
                .listeners((ApplicationListener<ApplicationStartingEvent>) event -> log.info("Application is starting..."))
                .listeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> log.info("Application environment is prepared."))
                .listeners((ApplicationListener<ApplicationStartedEvent>) event -> log.info("Application is started at {}.", event.getTimestamp()))
                .listeners((ApplicationListener<ApplicationReadyEvent>) event -> log.info("Application is ready now."))
                .run(args);
	}

	@RestController
    @RequestMapping("api")
    public static class MainController{
	    @GetMapping
        public String helloWorld(){
	        return "Hello World!";
        }
    }

    @Component
    @Order(2)
    public class ArgsPrinter {
        @Autowired
        public ArgsPrinter(ApplicationArguments args) {
            boolean debug = args.containsOption("debug");
            List<String> files = args.getNonOptionArgs();
            log.info("debug : {}, files : {}", debug, files.toString());
            // if run with "--debug logfile.txt" debug=true, files=["logfile.txt"]
        }
    }

    @Component
    @Order(1)
    public class CLRImple2 implements CommandLineRunner {
        public void run(String... args) {
            if(args.length > 1) log.info("2. args : {}", args[1]);
        }
    }

    @Component
    @Order(0)
    public class CLRImple1 implements CommandLineRunner {
        public void run(String... args) {
            if(args.length > 0) log.info("1. args : {}", args[0]);
        }
    }
}
