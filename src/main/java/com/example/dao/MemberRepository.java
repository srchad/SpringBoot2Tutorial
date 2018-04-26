package com.example.dao;

import com.example.myproject.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member, Long> {

    List<Member> findByLogonId(String logonid);

}