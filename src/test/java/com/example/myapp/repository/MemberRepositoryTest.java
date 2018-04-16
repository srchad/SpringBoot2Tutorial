package com.example.myapp.repository;

import com.example.myapp.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataNeo4jTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void getMemberByName() {
        Member chris = new Member();
        chris.setName("chris");
        chris.setAge(38);
        chris.setAddress("Suwon");
        memberRepository.save(chris);

        Member genos = new Member();
        genos.setName("genos");
        genos.setAge(30);
        genos.setAddress("Seoul");
        memberRepository.save(genos);

        List<Member> memberList = memberRepository.getMemberByName("chris");

        memberList.forEach(member -> {
            System.out.println(member);
        });
    }
}
