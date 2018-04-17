package com.example.myproject.application.webflux;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class MyRestController {


    @GetMapping("/{user}")
    public Mono<User> getUser(@PathVariable Long user) {
        return  Mono.just( new User(user)  );
    }

    @GetMapping("/{user}/customers")
    public Flux<Customer> getUserCustomers(@PathVariable Long user) {
        // ...
        return  Flux.empty();
    }

    @DeleteMapping("/{user}")
    public Mono<User> deleteUser(@PathVariable Long user) {
        return  Mono.empty();
    }

}