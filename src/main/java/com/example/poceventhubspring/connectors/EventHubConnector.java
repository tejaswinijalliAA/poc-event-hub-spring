package com.example.poceventhubspring.connectors;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventHubConnector {

    private final Sinks.Many<Message<String>> many;

    public void publishMessage(String request){
        log.info ("Emit next: {}", request);
        Message<String> message = MessageBuilder.withPayload (request)
                                                .build ();
        many.emitNext (message, Sinks.EmitFailureHandler.FAIL_FAST);
        log.info ("Published service bus message to queue");
    }
}
