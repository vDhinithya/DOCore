package com.docore.consumer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "app-logs")
public class LogEvent {
    @Id
    private String id;
    private String serviceName;
    private String logLevel;
    private String message;
    private String statusCode;
    private String timestamp;

}
