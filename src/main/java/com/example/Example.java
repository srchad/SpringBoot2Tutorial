package com.example;

import com.example.myapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.Resources;

@SpringBootApplication
public class Example {

    @Autowired
    MemberRepository memberRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            memberRepository.getMemberByName("chris").forEach(member -> {
                System.out.println(member);
            });
        };
    }
}
