package com.teamcubation.reportservice.domain.model.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    private String id;
    private String title;
    private String userEmail;
    private String downloadUrlPdf;
    private String downloadUrlCsv;
    private LocalDateTime creationDate;
    private String typeOfFinancialAsset;
    private String content;
}
