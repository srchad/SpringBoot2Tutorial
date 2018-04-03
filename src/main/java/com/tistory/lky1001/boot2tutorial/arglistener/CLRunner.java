package com.tistory.lky1001.boot2tutorial.arglistener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CLRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner :" + Arrays.asList(args).toString());
    }
}
