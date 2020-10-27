package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive spring")
                //uncomment this to find onError() getting executed
//                .concatWith(Flux.error(new RuntimeException("Exception occurred!")))
                .log();

        stringFlux
                .subscribe(System.out::println,
                        (ex) -> System.err.println("Exception is" + ex));
    }
}