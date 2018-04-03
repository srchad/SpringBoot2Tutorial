package com.example.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(CoreApplication.class)
                .child(WebApplication.class)
                .bannerMode(Banner.Mode.LOG)
                .run(args);
	}

	@RestController
    @RequestMapping("api")
    public static class MainController{
	    @GetMapping
        public String helloWorld(){
	        return "Hello World!";
        }
    }
}
