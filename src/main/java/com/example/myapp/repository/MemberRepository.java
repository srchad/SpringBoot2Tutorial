package com.example.myapp.repository;

import com.example.myapp.domain.Member;

public interface MemberRepository {
    Member getMemberByName(String name);
}
