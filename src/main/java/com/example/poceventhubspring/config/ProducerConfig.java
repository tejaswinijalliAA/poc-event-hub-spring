package com.example.poceventhubspring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.retry.Retry;

import java.util.function.Supplier;

@Configuration
@Slf4j
public class ProducerConfig {

    @Bean
    public Sinks.Many<Message<String>> many(){
        return Sinks.many ()
                    .unicast ()
                    .onBackpressureBuffer ();
    }

    @Bean
    public Supplier<Flux<Message<String>>> supply(Sinks.Many<Message<String>> many){
        return () -> many.asFlux ()
                         .retryWhen (retrySpecs ())
                         .doOnNext (message -> log.info ("sending message to eventhub: {}", message))
                         .doOnError (t -> log.error ("Error publishing chargeback retry", t));
    }

    private Retry retrySpecs(){
        return Retry.backoff (3, java.time.Duration.ofSeconds (2))
                    .maxBackoff (java.time.Duration.ofSeconds (5))
                    .jitter (0.5)
                    .doBeforeRetry (r -> log.info ("Retrying..."))
                    .onRetryExhaustedThrow ((retryBackoffSpec, retrySignal) -> {
                        log.error ("Retries exhausted for publishing to eventhubs: ", retrySignal.failure ());
                        return new RuntimeException ("Retries exhausted ", retrySignal.failure ());
                    });
    }
}
