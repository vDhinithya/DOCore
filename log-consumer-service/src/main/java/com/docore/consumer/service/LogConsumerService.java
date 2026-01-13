package com.docore.consumer.service;

import com.docore.consumer.entity.LogEvent;
import com.docore.consumer.repository.LogRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class LogConsumerService {
    @Autowired
    private LogRepository logRepository;

    private static final Logger log = LoggerFactory.getLogger(LogConsumerService.class);

    //@KafkaListener(topics = "docore-logs-topic", groupId = "docore-log-consumer-group")
    //public void consumedLog(LogEvent logEvent, @Header Map<String, Object> headers) {
    @KafkaListener(topics = "${docore.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    @CircuitBreaker(name = "elasticBreaker", fallbackMethod = "fallbackForElastic")
    public void consumedLog(@Payload LogEvent event, @Header(value = "traceId", required = false) String traceId) {
        if (traceId != null) {
        }
        log.info("processing log : {} | traceID : {}", event.getMessage(), traceId);

        //System.out.println("Received Log: " + logEvent.getMessage());
        //log.info("kafka headers: {}",headers);
        //log.info("Received Log: {}",logEvent.getMessage());
        //log.info("Received Log: {} | TraceID: {}", event.getMessage(), headers.get("traceId"));
//        if ("POISON_PILL".equals(event.getMessage())) {
//            log.error("⚠ POISON PILL DETECTED! Simulating crash...");
//            throw new RuntimeException("Boom! This is a forced crash to test DLQ.");
//        }
        // TEST: NON-RETRYABLE EXCEPTION ---
//        this was to simulate what will happen if we received an invalid message
        logRepository.save(event);
    }

    public void fallbackForElastic(LogEvent event, String traceId, Throwable t) {
        log.error("Circuit open: elsatic search is down , Cause {}", t.getMessage());
        log.warn("[Fallback] droping logs to console: {}", event);
    }

}
