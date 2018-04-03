package com.tistory.lky1001.boot2tutorial.arglistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArgTestBean {

    @Autowired
    public ArgTestBean(ApplicationArguments args) {
        boolean debug = args.containsOption("debug");
        List<String> files = args.getNonOptionArgs();
        // if run with "--debug logfile.txt" debug=true, files=["logfile.txt"]

        System.out.println("ApplicationArguments :" + files.toString());
    }

}