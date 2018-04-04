package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
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
    public class MyBean {

        @Autowired
        public MyBean(ApplicationArguments args) {
            boolean debug = args.containsOption("debug");
            List<String> files = args.getNonOptionArgs();
            log.info("debug : {}, files : {}", debug, files.toString());
            // if run with "--debug logfile.txt" debug=true, files=["logfile.txt"]
        }
    }
}
