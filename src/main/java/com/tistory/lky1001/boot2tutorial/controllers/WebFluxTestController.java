package com.tistory.lky1001.boot2tutorial.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tests")
public class WebFluxTestController {

    @GetMapping("/{user}")
    public Mono<WebTestController.User> getUser(@PathVariable Long user) {
        return Mono.just(new WebTestController.User());
    }

    @GetMapping("/{user}/customers")
    public Flux<WebTestController.Customer> getUserCustomers(@PathVariable Long user) {
        return Flux.just(
                new WebTestController.Customer(),
                new WebTestController.Customer(),
                new WebTestController.Customer(),
                new WebTestController.Customer()
        );
    }

    @DeleteMapping("/{user}")
    public Mono<WebTestController.User> deleteUser(@PathVariable Long user) {
        return Mono.just(new WebTestController.User());
    }
}
