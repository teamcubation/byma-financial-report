package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper;

import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.ReportEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.util.validation.PersistenceValidation;

import java.time.LocalDateTime;

public class ReportPersistenceMapper {

    public static Report reportEntityToReportModel(ReportEntity reportEntity){
        PersistenceValidation.validateObjetNotNull(reportEntity);
        return Report.builder()
                .id(reportEntity.getId())
                .title(reportEntity.getTitle())
                .userEmail(reportEntity.getUserEmail())
                .creationDate(reportEntity.getCreationDate())
                .downloadUrlPdf(reportEntity.getDownloadUrlPdf())
                .downloadUrlCsv(reportEntity.getDownloadUrlCsv())
                .content(reportEntity.getContent())
                .build();

    }

    public static ReportEntity reportModelToReportEntity(Report report){
        PersistenceValidation.validateObjetNotNull(report);
        return ReportEntity.builder()
                .id(report.getId())
                .title(report.getTitle())
                .userEmail(report.getUserEmail())
                .creationDate(LocalDateTime.now())
                .downloadUrlPdf(report.getDownloadUrlPdf())
                .downloadUrlCsv(report.getDownloadUrlCsv())
                .content(report.getContent())
                .build();

    }
}
