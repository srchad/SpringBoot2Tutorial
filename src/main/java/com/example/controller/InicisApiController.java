package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.ApiResponse;
import com.example.model.InicisMember;
import com.example.service.InicisApiService;
import com.example.service.RedisService;
import com.example.service.RestService;

import reactor.core.publisher.Mono;

@RestController
public class InicisApiController {

    @Autowired
    private InicisApiService inicisApiService;

    @Autowired
    private RestService restService;

// TODO : 런타임 오류 남
    /*
    @Autowired
    private RedisService redisService;
*/

    @RequestMapping(value="/getInicisMember")
    List<InicisMember> home() {

        //redisService.testRedis();

        return inicisApiService.findAll();
    }

    @RequestMapping(value="/getJoinInfo")
    ApiResponse getJoinInfo() {

        return restService.getJoinInfoRestCall();
    }

    @RequestMapping(value="/getJoinInfoWebClient")
    Mono<ApiResponse> getJoinInfoWebClient() {

        return restService.getJoinInfoWebClient();
    }

}
