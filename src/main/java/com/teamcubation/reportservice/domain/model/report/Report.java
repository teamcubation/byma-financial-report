package com.teamcubation.reportservice.domain.model.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private LocalDateTime dateTime;
    private String title;
    private List<FilterReport> filters;
    private String format;
}
