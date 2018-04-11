package com.example.demo.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * @author genos.lee <genos.lee@kakaocorp.com>
 */
@Component
public class UserHandler {

    @Value("${hello.number.in.range}")
    private int randomNumber;

    @Value("${hello.members}")
    private String[] members;

    public Mono<ServerResponse> getUser(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromObject("getUser"));
    }

    public Mono<ServerResponse> getUserCustomers(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromObject("getUserCustomers"));
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromObject("deleteUser"));
    }


    public Mono<ServerResponse> next(final ServerRequest request) {
        final Map<String, Object> model = Collections.singletonMap("id", members[randomNumber]);
        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("next", model);
    }
}
