package com.example.demo;

import com.example.demo.handler.UserHandler;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


/**
 * @author genos.lee <genos.lee@kakaocorp.com>
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    ReactiveAuthenticationManager reactiveAuthenticationManager(){
        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsRepository());
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsRepository() {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        UserDetails admin = userBuilder.username("admin").password("admin").roles("USER", "ADMIN").build();
        return new MapReactiveUserDetailsService(admin);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .pathMatchers("/user/**", "/hello")
                .authenticated().and().authenticationManager(reactiveAuthenticationManager())
                .formLogin();
        return http.build();
    }
}
