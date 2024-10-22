package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper;

import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.ReportEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReportPersistenceMapper {

    public static Report reportEntityToReportModel(ReportEntity reportEntity){
        if (reportEntity == null) throw new RuntimeException();
        return Report.builder()
                .id(reportEntity.getId())
                .title(reportEntity.getTitle())
                .userEmail(reportEntity.getUserEmail())
                .creationDate(reportEntity.getCreationDate())
                .downloadUrlPdf(reportEntity.getDownloadUrlPdf())
                .downloadUrlCsv(reportEntity.getDownloadUrlCsv())
                .typeOfFinancialAsset(reportEntity.getTypeOfFinancialAsset())
                .content(reportEntity.getContent())
                .build();

    }

    public static ReportEntity reportModelToReportEntity(Report report){
        if (report == null) throw new RuntimeException();
        return ReportEntity.builder()
                .id(report.getId())
                .title(report.getTitle())
                .userEmail(report.getUserEmail())
                .creationDate(LocalDateTime.now())
                .downloadUrlPdf(report.getDownloadUrlPdf())
                .downloadUrlCsv(report.getDownloadUrlCsv())
                .typeOfFinancialAsset(report.getTypeOfFinancialAsset())
                .content(report.getContent())
                .build();

    }

}
