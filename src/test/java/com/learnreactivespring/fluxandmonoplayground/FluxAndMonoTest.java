package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive spring")
                .concatWith(Flux.error(new RuntimeException("Exception occured!")));
        stringFlux
                .subscribe(System.out::println,
                        (error) -> System.err.println(error));
    }
}