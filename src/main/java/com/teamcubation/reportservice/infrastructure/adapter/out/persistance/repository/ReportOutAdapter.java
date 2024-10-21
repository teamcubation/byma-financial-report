package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository;

import com.teamcubation.reportservice.application.port.out.ReportOutPort;
import com.teamcubation.reportservice.domain.model.Report;

import java.util.List;

public class ReportOutAdapter implements ReportOutPort {

    @Override
    public List<Report> findById() {
        return List.of();
    }

    @Override
    public List<Report> getAll() {
        return List.of();
    }
}
