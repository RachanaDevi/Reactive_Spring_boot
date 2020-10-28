package com.learnreactivespring.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@AutoConfigureWebTestClient(timeout = "9000")
class FluxControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void shouldValidateResponseUsingStepVerifier() {
        Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(5, 6, 7, 8)
                .verifyComplete();
    }

    @Test
    public void shouldValidateResponseByHeaderAndBodyListSize() {
        webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Integer.class)
                .hasSize(4);
    }

    @Test
    public void shouldValidateResponseByComparingResponseWithList() {
        List<Integer> expectedIntegerList = Arrays.asList(5, 6, 7, 8);

        EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();

        assertThat(entityExchangeResult.getResponseBody(), is(expectedIntegerList));
    }

    @Test
    void shouldValidateResponseUsingConsumeWithUsedFromExpectBodyList() {
        List<Integer> expectedIntegerList = Arrays.asList(5, 6, 7, 8);

        webTestClient
                .get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .consumeWith((response) -> assertThat(response.getResponseBody(), is(expectedIntegerList)));
    }

    @Test
    public void shouldValidateResponseForInfiniteValues() {
        Flux<Long> longFlux = webTestClient.get().uri("/fluxinfinite")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(longFlux)
                .expectNext(0L,1L,2L)
                .thenCancel()
                .verify();

    }
}