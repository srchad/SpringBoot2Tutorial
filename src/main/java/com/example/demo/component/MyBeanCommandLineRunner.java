package com.example.demo.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author genos.lee <genos.lee@kakaocorp.com>
 */
@Component
public class MyBeanCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("commandlinerunner is detected!!!!!!!!!!!!!!!");
        for(String arg: args) {
            System.out.println(arg);
        }
    }
}

