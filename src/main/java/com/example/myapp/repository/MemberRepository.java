package com.example.myapp.repository;

import com.example.myapp.domain.Member;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface MemberRepository extends ReactiveMongoRepository<Member, String> {

    Flux<Member> getMemberByName(String name);
}
