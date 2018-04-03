package com.tistory.lky1001.boot2tutorial;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Boot2tutorialApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Boot2tutorialApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
