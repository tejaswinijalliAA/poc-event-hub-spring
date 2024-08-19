package com.example.poceventhubspring.service;

import com.example.poceventhubspring.connectors.EventHubConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerService {

    private final EventHubConnector eventHubConnector;

    public void publishMessage(String message){
        log.info ("Publishing message: {}", message);
        eventHubConnector.publishMessage (message);
        log.info ("Published message to event hub");
    }
}
