package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive spring")
                //uncomment this to find onError() getting executed
//                .concatWith(Flux.error(new RuntimeException("Exception occurred!")))
                .concatWith(Flux.just("After error"))
                .log();

        stringFlux
                .subscribe(System.out::println,
                        (ex) -> System.err.println("Exception is" + ex),
                        () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElementsWithoutError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                // the order should be the same, it can't be different or it will fail
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();
    }
}