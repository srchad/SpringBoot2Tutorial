package com.example;

import com.example.myapp.domain.Member;
import com.example.myapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
            Member member = new Member();
            member.setName("chris");
            member.setAge(38);
            member.setAddress("Suwon");
            memberRepository.save(member);
        };
    }

}
