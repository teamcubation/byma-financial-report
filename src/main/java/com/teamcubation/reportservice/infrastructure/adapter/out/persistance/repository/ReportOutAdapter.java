package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository;

import com.teamcubation.reportservice.application.port.out.ReportOutPort;
import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.ReportEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.ReportPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;
import java.util.stream.Collectors;
@Repository
@RequiredArgsConstructor
public class ReportOutAdapter implements ReportOutPort {

    private final ReportRepository reportRepository;

    @Cacheable(value = "reportsByUserEmail", key = "#email")
    @Override
    public List<Report> findByUserEmail(String email) {
        return reportRepository.findByUserEmail(email).stream()
                .map(ReportPersistenceMapper::reportEntityToReportModel)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "allReports")
    @Override
    public List<Report> getAll() {
        return reportRepository.findAll().stream()
                .map(reportEntity -> ReportPersistenceMapper.reportEntityToReportModel(reportEntity))
                .collect(Collectors.toList());
    }

    @CachePut(value = "reports", key = "#report.email")
    public ReportEntity save(Report report) {
        return reportRepository.save(ReportPersistenceMapper.reportModelToReportEntity(report));
    }
}
