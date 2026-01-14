package com.docore.consumer.service;

import com.docore.consumer.entity.LogEvent;
import com.docore.consumer.repository.LogRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogConsumerService {

    @Autowired
    private LogRepository logRepository;
    private static final Logger log = LoggerFactory.getLogger(LogConsumerService.class);

    @KafkaListener(topics = "${docore.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    @CircuitBreaker(name = "elasticBreaker", fallbackMethod = "fallbackForElastic")
    public void consumedLog(@Payload(required = false) LogEvent event,
                            @Header(value = "traceId", required = false) String traceId) {
        if (event == null) {
            log.warn("⚠ Received null event from Kafka. Skipping processing.");
            return;
        }
        if (traceId == null) {
            traceId = "N/A";
        }
        log.info("processing log : {} | traceID : {}", event.getMessage(), traceId);

        logRepository.save(event);
    }

    public void fallbackForElastic(LogEvent event, String traceId, Throwable t) {
        log.error("Circuit open: elsatic search is down , Cause {}", t.getMessage());
        log.warn("[Fallback] droping logs to console: {}", event);
    }

}
