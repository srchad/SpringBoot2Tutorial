package com.example.myapp.repository;

import com.example.myapp.domain.Member;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class MemberRepositoryImpl implements MemberRepository {

//    @Override
//    public Member getMemberByName(String name) {
//    simulateSlowService();
//    System.out.println("메소드 호출 =======> name : " + name);
//    return new Member(name, 38, "Suwon");
//}

    @Override
    @Cacheable(key = "#name", value = "chris")
    public Member getMemberByName(String name) {
        simulateSlowService();
        System.out.println("메소드 호출 =======> name : " + name);
        return new Member(name, 38, "Suwon");
    }

    private void simulateSlowService() {
        try {
            long time = 1000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
