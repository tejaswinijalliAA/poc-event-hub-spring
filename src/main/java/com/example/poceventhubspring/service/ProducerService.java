package com.example.poceventhubspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@Slf4j
public class ProducerService {

    private final Sinks.Many<Message<String>> araMessageSinks;

    public ProducerService(Sinks.Many<Message<String>> araMessageSinks){this.araMessageSinks = araMessageSinks;}

    public void produceMessage(String message){
        log.info ("sending message {}", message);
        araMessageSinks.emitNext (MessageBuilder.withPayload (message).build (), Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
