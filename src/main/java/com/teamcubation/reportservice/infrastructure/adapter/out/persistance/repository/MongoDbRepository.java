package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository;

import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.ReportEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDbRepository extends MongoRepository<ReportEntity, Long> {
}
