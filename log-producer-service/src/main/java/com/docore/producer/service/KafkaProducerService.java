package com.docore.producer.service;

import com.docore.producer.entity.LogEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, LogEvent> kafkaTemplate;
    
    @Value("${docore.kafka.topic}")
    private String topicName;


    public KafkaProducerService(KafkaTemplate<String, LogEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLog(LogEvent event) {
        kafkaTemplate.send(topicName, event);
        System.out.println("sent to kafka: "+ event.getMessage());
    }

}
