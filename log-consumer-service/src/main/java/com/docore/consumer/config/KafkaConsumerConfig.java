package com.docore.consumer.config;

import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import javax.sql.rowset.serial.SerialException;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    /*
     * Configures the error handler for Kafka consumers.
     * Logic:
     * 1. Tries to process the message.
     * 2. If it fails, it waits for 1 second and tries again.
     * 3. It retries a maximum of 2 times.
     * 4. If it still fails, it sends the message to the Dead Letter Queue (DLQ).
     */
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, Object> kafkaTemplate) {
        // Recoverer: Sends the failed message to "original-topic.DLQ"
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate, (record, ex) -> {
            System.err.println(" FAILURE DETECTED: " + ex.getMessage());
            System.err.println(" MOVING RECORD TO DLQ: " + record.topic() + ".DLQ");
            return new TopicPartition(record.topic() + ".DLQ", record.partition());
        });

        // Backoff: Wait 1 second, retry max 2 times
        FixedBackOff backOff = new FixedBackOff(1000L, 2);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);
        // Exceptions that should not be retried
        errorHandler.addNotRetryableExceptions(IllegalArgumentException.class, SerializationException.class, NullPointerException.class);

        //        Return the handler
        return errorHandler;
    }
}
