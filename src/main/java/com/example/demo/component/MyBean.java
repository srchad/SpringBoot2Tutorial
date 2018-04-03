package com.example.demo.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author genos.lee <genos.lee@kakaocorp.com>
 */
@Component
public class MyBean {

    @Autowired
    public MyBean(ApplicationArguments args) {
        boolean argtest = args.containsOption("argtest");
        if(argtest) {
            System.out.println("argtest is detected!!!!!!!!!!!!!!!");
        }
        List<String> files = args.getNonOptionArgs();
        for(String file: files){
            System.out.println(file);
        }
    }

}

