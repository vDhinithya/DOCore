package com.docore.consumer.repository;

import com.docore.consumer.entity.ErrorLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends ElasticsearchRepository<ErrorLog, String> {
}
