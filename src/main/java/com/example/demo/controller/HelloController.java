package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author genos.lee <genos.lee@kakaocorp.com>
 */
@RestController
public class HelloController {
    final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${hello.text}")
    private String text;

    @Value("${hello.number.in.range}")
    private int randomNumber;

    @Value("${hello.members}")
    private String[] members;

    @GetMapping("/hello")
    public String test(){
        logger.info("test");
        return text;
    }

    @GetMapping("/next")
    public ModelAndView next()
    {
        ModelAndView result = new ModelAndView();
        result.addObject("id",  members[randomNumber]);
        return result;
    }
}
