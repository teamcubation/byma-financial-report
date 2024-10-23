package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository;

import com.teamcubation.reportservice.application.port.out.ReportOutPort;
import com.teamcubation.reportservice.domain.model.report.Report;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReportOutAdapter implements ReportOutPort {

    private ReportRepository reportRepository;

    @Override
    public List<Report> findByUserId(long id) {
        return List.of();
    }

    @Override
    public List<Report> getAll() {
        return List.of();
    }
}
