package com.learnreactivespring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class FluxMonoController {

    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> returnFluxStream() {
        return Flux.just(1, 2, 3, 4)
                .delayElements(Duration.ofSeconds(2))
                .log();
    }

    @GetMapping("/flux")
    public Flux<Integer> returnFlux() {
        return Flux.just(5, 6, 7, 8)
                .delayElements(Duration.ofSeconds(2))
                .log();
    }
}
