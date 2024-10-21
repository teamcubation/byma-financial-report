package com.teamcubation.reportservice.application.port.out;

import com.teamcubation.reportservice.domain.model.Report;

import java.util.List;

public interface ReportOutPort {
    List<Report> findById();
    List<Report> getAll();
}
