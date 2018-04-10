package com.tistory.lky1001.boot2tutorial.handlers;

import com.tistory.lky1001.boot2tutorial.controllers.WebTestController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class UserHandler {

    public Mono<ServerResponse> getUser(ServerRequest serverRequest) {
        return ServerResponse.ok().build();
    }

    public Mono<ServerResponse> getUserCustomers(ServerRequest serverRequest) {
        return Mono.create(s -> {
            System.out.println(Thread.currentThread().getName());

            s.success(ServerResponse.ok()
                .body(Mono.just("hello " + serverRequest.queryParam("name")), String.class).block());
        });
//        return ServerResponse.ok()
//                .body(Mono.just("hello world"), String.class);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(Mono.just("deleted " + serverRequest.queryParam("name")), String.class);
    }

    public Mono<ServerResponse> getUserDetails(ServerRequest serverRequest) {
        Flux<WebTestController.User> users = Flux.create(s -> {
            System.out.println(Thread.currentThread().getName());

            WebTestController.User user1 = new WebTestController.User();
            user1.id = 1;

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!s.isCancelled()) {
                s.next(user1);
            }

            WebTestController.User user2 = new WebTestController.User();
            user2.id = 2;

            // 한번 섭스크라이브 하고 dispose 해버림
            if (!s.isCancelled()) {
                s.next(user2);
            }

            if (!s.isCancelled()) {
                s.complete();
            }
        });

        return ServerResponse.ok().body(users, WebTestController.User.class);
    }

    public Mono<ServerResponse> getUserDetailTest(ServerRequest serverRequest) {
        Flux<WebTestController.User> users = Flux.create(s -> {
            System.out.println(Thread.currentThread().getName());

            WebTestController.User user1 = new WebTestController.User();
            user1.id = 1;

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!s.isCancelled()) {
                s.next(user1);
            }


            WebTestController.User user2 = new WebTestController.User();
            user2.id = 2;

            if (!s.isCancelled()) {
                s.next(user2);
            }

            if (!s.isCancelled()) {
                s.complete();
            }
        });

        return ServerResponse.ok().body(users, WebTestController.User.class).subscribeOn(Schedulers.parallel());
    }
}
