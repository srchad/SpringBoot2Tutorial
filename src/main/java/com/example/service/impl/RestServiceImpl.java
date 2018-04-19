package com.example.service.impl;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.model.ApiResponse;
import com.example.service.RestService;

import reactor.core.publisher.Mono;

@Service
public class RestServiceImpl implements RestService{

    private final RestTemplate restTemplate;

    private final WebClient webClient;


    public RestServiceImpl(RestTemplateBuilder restTemplateBuilder, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.webClient = webClientBuilder.baseUrl("http://clix-internal-api.dev.biz.daum.net:8080").build();
    }

    public ApiResponse getJoinInfoRestCall(){
        return this.restTemplate.getForObject("http://clix-internal-api.dev.biz.daum.net:8080/extcorp/joininfo?bizNumber=111", ApiResponse.class);
    }

    public Mono<ApiResponse> getJoinInfoWebClient(){
        return this.webClient.get().uri("/extcorp/joininfo?bizNumber=111")
                             .retrieve().bodyToMono(ApiResponse.class);
    }

}


