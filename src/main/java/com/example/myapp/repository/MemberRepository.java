package com.example.myapp.repository;

import com.example.myapp.domain.Member;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MemberRepository extends PagingAndSortingRepository<Member, String> {

    List<Member> getMemberByName(String name);
}
