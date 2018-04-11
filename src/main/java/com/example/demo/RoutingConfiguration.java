package com.example.demo;

import com.example.demo.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


/**
 * @author genos.lee <genos.lee@kakaocorp.com>
 */
@Configuration
public class RoutingConfiguration {

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler) {
        return route(GET("/user/{user}").and(accept(APPLICATION_JSON)), userHandler::getUser)
                .andRoute(GET("/user/{user}/customers").and(accept(APPLICATION_JSON)), userHandler::getUserCustomers)
                .andRoute(DELETE("/user/{user}").and(accept(APPLICATION_JSON)), userHandler::deleteUser)
                .andRoute(GET("/user/random/next"), userHandler::next);
    }

}
