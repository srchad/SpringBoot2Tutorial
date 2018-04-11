package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.Rendering;

/**
 * @author genos.lee <genos.lee@kakaocorp.com>
 */
@RestController
public class HelloController {
    final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${hello.text}")
    private String text;



    @GetMapping("/hello")
    public String test(){
        logger.info("test");
        return text;
    }

//    @GetMapping("/next")
//    public String next(final Model model)
//    {
//        model.addAttribute("id", members[randomNumber]);
//        return "next";
//        return Rendering.view("next")
//                .modelAttribute("id", members[randomNumber])
//                .build();
//    }
}
