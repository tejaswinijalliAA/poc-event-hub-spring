package com.example.poceventhubspring.config;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
@Slf4j
public class ProducerConfig {

    @Bean
    public Sinks.Many<Message<String>> araMessageSinks(){
        return Sinks.many ().unicast ().onBackpressureBuffer ();
    }

    @Bean
    public Supplier<Flux<Message<String>>> produceMessageToARA(Sinks.Many<Message<String>> araMessageSinks){
        return () -> araMessageSinks.asFlux ()
                                    .doOnNext (m -> log.info ("sending message {}", m))
                                    .doOnError (t -> log.error ("Error publishing chargeback retry", t));
    }
}
