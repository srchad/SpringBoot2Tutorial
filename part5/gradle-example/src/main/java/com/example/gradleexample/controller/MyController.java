package com.example.gradleexample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/pricing")
    public String pricing() {
        return "pricing";
    }

    @RequestMapping("/sign-in")
    public String signIn() {
        return "sign-in";
    }
}
