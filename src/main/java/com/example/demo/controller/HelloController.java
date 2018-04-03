package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author genos.lee <genos.lee@kakaocorp.com>
 */
@RestController
public class HelloController {
    @GetMapping("/")
    public String test(){
        return "test";
    }

}
