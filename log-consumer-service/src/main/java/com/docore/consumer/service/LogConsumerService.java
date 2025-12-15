package com.docore.consumer.service;

import com.docore.consumer.entity.LogEvent;
import com.docore.consumer.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class LogConsumerService {
    @Autowired
    private LogRepository logRepository;

    private static final Logger log = LoggerFactory.getLogger(LogConsumerService.class);

    @KafkaListener(topics = "docore-logs-topic", groupId = "docore-log-consumer-group")
    public void consumedLog(LogEvent logEvent, @Header Map<String, Object> headers) {
        //System.out.println("Received Log: " + logEvent.getMessage());
        //log.info("kafka headers: {}",headers);
        log.info("Received Log: {}",logEvent.getMessage());

        logRepository.save(logEvent);
    }

}
