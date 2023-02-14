package com.example.study.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping("/webflux")
@Slf4j
public class WebFluxController {

    @Autowired
    private WebClient springWebClient;

    private static AtomicInteger CALL_COUNT = new AtomicInteger(0);

    @GetMapping("/find/{count}")
    public void find(@PathVariable int count) {
        int current = 0;

        log.info("Call Count: {}", CALL_COUNT.incrementAndGet());

        while(current <= count) {
            try {
                Thread.sleep(1000);
                current++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/call")
    public void call() {
        springWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/webflux/find/{count}")
                        .build(3))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new RuntimeException(">>>>>>>>>>>> find error")))
                .bodyToMono(Void.class)
                .subscribe();
    }
}
