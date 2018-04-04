package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author genos.lee <genos.lee@kakaocorp.com>
 */
@RestController
public class HelloController {

    @Value("${hello.text}")
    private String text;

    @Value("${hello.number.in.range}")
    private int randomNumber;

    @Value("${hello.members}")
    private String[] members;

    @GetMapping("/")
    public String test(){
        return text;
    }

    @GetMapping("/next")
    public String next() {
        return members[randomNumber];
    }
}
