package com.docore.consumer.repository;

import com.docore.consumer.entity.LogEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends ElasticsearchRepository<LogEvent, String> {
}
