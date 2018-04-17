package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.InicisMember;
import com.example.service.InicisApiService;
import com.example.service.RedisService;

@RestController
public class InicisApiController {

    @Autowired
    private InicisApiService inicisApiService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value="/getInicisMember")
    List<InicisMember> home() {

        redisService.testRedis();

        return inicisApiService.findAll();
    }

}
