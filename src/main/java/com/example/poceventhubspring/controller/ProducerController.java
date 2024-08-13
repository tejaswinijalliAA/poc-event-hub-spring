package com.example.poceventhubspring.controller;

import com.example.poceventhubspring.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProducerController {
    private final ProducerService producerService;

    @PostMapping("/publish")
    public ResponseEntity<String> produceMessage(@RequestBody String message){
        producerService.produceMessage (message);
        return ResponseEntity.ok ("Message sent to eventhub");
    }
}
