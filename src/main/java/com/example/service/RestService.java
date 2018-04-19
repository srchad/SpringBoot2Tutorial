package com.example.service;

import com.example.model.ApiResponse;

import reactor.core.publisher.Mono;

public interface RestService {

    ApiResponse getJoinInfoRestCall();

    Mono<ApiResponse> getJoinInfoWebClient();
}
