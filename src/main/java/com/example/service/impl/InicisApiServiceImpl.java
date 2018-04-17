package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.InicisMember;
import com.example.repository.InicisMemberRepository;
import com.example.service.InicisApiService;
import com.google.common.collect.Lists;

@Transactional
@Service
public class InicisApiServiceImpl implements InicisApiService{

    @Autowired
    private InicisMemberRepository repository;

    @Override
    public List<InicisMember> findAll() {
        return Lists.newArrayList(repository.findAll());
    }
}
