package com.docore.producer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEvent {
    private String serviceName;
    private String logLevel;
    private String message;
    private String statusCode;
    private String timestamp;
}
