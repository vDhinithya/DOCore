package com.docore.consumer.service;

import com.docore.consumer.entity.LogEvent;
import com.docore.consumer.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LogConsumerService {
    @Autowired
    private LogRepository logRepository;

    @KafkaListener(topics = "docore-logs-topic", groupId = "docore-log-consumer-group")
    public void consumedLog(LogEvent logEvent) {
        System.out.println("Received Log: " + logEvent.getMessage());

        logRepository.save(logEvent);
    }

}
