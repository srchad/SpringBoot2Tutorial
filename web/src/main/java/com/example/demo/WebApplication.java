package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 42;
    }

    @RestController
    @RequestMapping("api")
    public static class MainController {
        @Value("${user.name}") private String property;

        @GetMapping
        public String helloWorld() {
            // ./gradlew :web:bootjar && java -jar web/build/libs/web.jar --debug log.txt --user.name=Frank.park& open "localhost:8080"
            return "Hello " + property + "!";
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
            if (args.length > 1) log.info("2. args : {}", args[1]);
        }
    }

    @Component
    @Order(0)
    public class CLRImple1 implements CommandLineRunner {
        public void run(String... args) {
            if (args.length > 0) log.info("1. args : {}", args[0]);
        }
    }

    @Component
    @Order(3)
    public class CLRImple3 implements CommandLineRunner {
        @Value("${my.secret}")
        private String secret;
        
        @Value("${my.number}")
        private int number;

        @Value("${my.bignumber}")
        private long bignumber;

        @Value("${my.uuid}")
        private String uuid;

        @Value("${my.number.less.than.ten}")
        private int numberLessThanTen;

        @Value("${my.number.in.range}")
        private int numberInRange;


        public void run(String... args) {
            log.info("random values = [ secret : {}, number : {}, bignumber : {}, uuid : {}, numberLessThanTen : {}, numberInRange : {} ]",
                    secret, number, bignumber, uuid, numberLessThanTen, numberInRange);
        }
    }
}
