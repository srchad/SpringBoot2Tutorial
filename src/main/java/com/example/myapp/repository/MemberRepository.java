package com.example.myapp.repository;

import com.example.myapp.domain.Member;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;


public interface MemberRepository extends Neo4jRepository<Member, String> {

    List<Member> getMemberByName(String name);
}
