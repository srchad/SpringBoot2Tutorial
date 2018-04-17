package com.example;

import com.example.myapp.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class Example {

    private static final Logger logger = LoggerFactory.getLogger(Example.class);

    @Autowired
    MemberRepository memberRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            logger.info("===============");
            logger.info("chris : " + memberRepository.getMemberByName("chris"));
            logger.info("daniel : " + memberRepository.getMemberByName("daniel"));
            logger.info("genos : " + memberRepository.getMemberByName("genos"));
            logger.info("frank : " + memberRepository.getMemberByName("frank"));
            logger.info("chris : " + memberRepository.getMemberByName("chris"));
            logger.info("colin : " + memberRepository.getMemberByName("colin"));
            logger.info("chris : " + memberRepository.getMemberByName("chris"));
            logger.info("chris : " + memberRepository.getMemberByName("chris"));
        };
    }

}
