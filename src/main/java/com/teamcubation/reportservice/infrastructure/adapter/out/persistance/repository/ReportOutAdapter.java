package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository;

import com.teamcubation.reportservice.application.port.out.ReportOutPort;
import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.ReportEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.ReportPersistenceMapper;
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
        log.info("Entro a la bdd - findByUserEmail");
        return reportRepository.findByUserEmail(email);
    }

    @Override
    public List<ReportEntity> getAll() {
        log.info("Entro a la bdd - getAll");
        return reportRepository.findAll();
    }

    @Override
    public ReportEntity save(Report report) {
        log.info("Entro a la bdd - save");
        return reportRepository.save(ReportPersistenceMapper.reportModelToReportEntity(report));
    }
}
