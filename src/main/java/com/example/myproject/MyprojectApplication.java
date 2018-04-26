package com.example.myproject;

import com.example.dao.MemberRepository;
import com.example.listener.ApplicationEventListener;
import javafx.application.Application;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class MyprojectApplication {

	@Autowired
	MemberRepository memberRepository;

	public static void main(String[] args) {
//		SpringApplication app = new SpringApplication(MyprojectApplication.class);
//		app.setBannerMode(Banner.Mode.LOG);
//		app.setApplicationContextClass(ReactiveWebServerApplicationContext.class);
//		app.run(args);

//		// 23.4 SpringApplicationBuilder
//		new SpringApplicationBuilder()
//				.sources(Parent.class)
//				.child(Application.class)
//				.bannerMode(Banner.Mode.LOG)
//				.listeners(new ApplicationEventListener()) // 23.5 Application Events and Listeners
//				.run(args);
		SpringApplication.run(Application.class, args);
	}

	@Transactional(readOnly = true)
	public void run(String... args) throws Exception {
		for (Member member : memberRepository.findByLogonId("lky1001")) {
			System.out.println(member);
		}
	}
}
