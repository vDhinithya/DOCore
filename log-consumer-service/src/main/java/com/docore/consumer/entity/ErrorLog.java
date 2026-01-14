package com.docore.consumer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "docore-error-logs")
public class ErrorLog {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String originalPayload; // The raw data that caused the crash

    @Field(type = FieldType.Keyword)
    private String failureReason;   // "SerializationException", "NullPointerException"

    @Field(type = FieldType.Text)
    private String stackTrace;      // The full error details

    @Field(type = FieldType.Keyword) // To get the trace id
    private String traceId;

    @Field(type = FieldType.Date)
    private LocalDateTime timestamp;

    public ErrorLog(String originalPayload, String failureReason, String stackTrace, String traceId) {
        this.originalPayload = originalPayload;
        this.failureReason = failureReason;
        this.stackTrace = stackTrace;
        this.traceId = traceId;
        this.timestamp = LocalDateTime.now();
    }
}
