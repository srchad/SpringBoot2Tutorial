package com.example.myproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SpringBootApplication
public class MyBean implements CommandLineRunner {

    public void run(String... args) {
        System.out.println("Start with CommandLineRunner");
    }

    public static void main(String[] args) {
        //MyBean myBean = new MyBean();
        //myBean.run( "test" );
        SpringApplication.run(MyBean.class, args);
    }

}