package com.docore.producer.controller;

import com.docore.producer.entity.LogEvent;
import com.docore.producer.service.KafkaProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final KafkaProducerService producerService;

    public LogController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public String publishLog(@RequestBody LogEvent event) {
        // Auto-add timestamp if you didn't send one
        if (event.getTimestamp() == null) {
            event.setTimestamp(LocalDateTime.now().toString());
        }
        producerService.sendLog(event);
        return "Log sent successfully!";
    }
}