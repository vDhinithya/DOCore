package com.docore.consumer.service;

import com.docore.consumer.entity.ErrorLog;
import com.docore.consumer.repository.ErrorLogRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DlqConsumerService {
    private static final Logger log = LoggerFactory.getLogger(DlqConsumerService.class);
    private final ErrorLogRepository errorLogRepository;

    @KafkaListener(topics = "${docore.kafka.topic}.DLQ", groupId = "docore-dlq-group",
            properties = {
                    "key.deserializer=org.apache.kafka.common.serialization.StringDeserializer",
                    "value.deserializer=org.apache.kafka.common.serialization.StringDeserializer"
            })
    public void listenTODlq(
            @Payload String rawPayload,
            @Header(KafkaHeaders.DLT_EXCEPTION_MESSAGE) String exceptionMessage,
            @Header(KafkaHeaders.DLT_EXCEPTION_STACKTRACE) String stackTrace,
            @Header(value = "traceId", required = false) String traceId
    ){
        log.info("DLQ Received Failed Message. Reason: {}", exceptionMessage);
        ErrorLog errorLog = new ErrorLog(rawPayload, exceptionMessage, stackTrace, traceId);
        errorLogRepository.save(errorLog);
        log.info("Saved failure report to Elasticsearch.");
    }
}

