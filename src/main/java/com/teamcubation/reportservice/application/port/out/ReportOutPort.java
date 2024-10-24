package com.teamcubation.reportservice.application.port.out;

import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.ReportEntity;

import java.util.List;

public interface ReportOutPort {
    List<ReportEntity> findByUserEmail(String email);
    List<ReportEntity> getAll();
    ReportEntity save(Report report);

}
