package com.teamcubation.reportservice.application.port.out;

import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.ReportEntity;

import java.util.List;

public interface ReportOutPort {
    List<Report> findByUserEmail(String email);
    List<Report> getAll();
    ReportEntity save(Report report);

}
