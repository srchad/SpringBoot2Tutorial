package kakao.springboot.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class WebFluxController {

    @GetMapping("/foo")
    public String getFoo() {
        return "colin";
    }

    @GetMapping("/flux") //item이 n개
    public Flux<String> flux() {
        return Flux.just("a","b","c");
    }

    @GetMapping("/mono") //item이 1개
    public Mono<String> mono() {
        return Mono.just("mono");
    }
}
