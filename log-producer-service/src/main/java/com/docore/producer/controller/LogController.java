package com.docore.producer.controller;

import com.docore.producer.entity.LogEvent;
import com.docore.producer.service.KafkaProducerService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
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
    @RateLimiter(name = "ingestLimiter", fallbackMethod = "rateLimitFallback")
    public ResponseEntity<String> publishLog(@RequestBody LogEvent event) {
        if (event.getTimestamp() == null) {
            event.setTimestamp(LocalDateTime.now().toString());
        }
        producerService.sendLog(event);
        return ResponseEntity.ok("Log sent successfully to Kafka topic!");
    }

    public ResponseEntity<String> rateLimitFallback(LogEvent event, RequestNotPermitted ex) {
        return ResponseEntity.status(429).body("Too many requests! Slow down man");
    }

}