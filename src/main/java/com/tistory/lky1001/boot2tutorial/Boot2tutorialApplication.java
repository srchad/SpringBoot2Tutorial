package com.tistory.lky1001.boot2tutorial;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Boot2tutorialApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Boot2tutorialApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}
}
