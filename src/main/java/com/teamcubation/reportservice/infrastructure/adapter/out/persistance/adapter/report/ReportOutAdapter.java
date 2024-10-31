package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.report;

import com.teamcubation.reportservice.application.port.out.ReportOutPort;
import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.ReportEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.exception.reportException.InvalidObjectException;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.ReportPersistenceMapper;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReportOutAdapter implements ReportOutPort {

    private final ReportRepository reportRepository;

    @Override
    public List<ReportEntity> findByUserEmail(String email) {
        return reportRepository.findByUserEmail(email);
    }

    @Override
    public List<ReportEntity> getAll() {
        return reportRepository.findAll();
    }

    @Override
    public ReportEntity save(Report report) throws InvalidObjectException {
        return reportRepository.save(ReportPersistenceMapper.reportModelToReportEntity(report));
    }

    @Override
    public ReportEntity findById(String id) {
        return reportRepository.findById(id).orElseThrow();
    }
}
