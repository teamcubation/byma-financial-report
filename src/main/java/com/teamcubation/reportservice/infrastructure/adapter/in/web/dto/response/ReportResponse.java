package com.teamcubation.reportservice.infrastructure.adapter.in.web.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ReportResponse {
    private String title;
    private LocalDateTime creationDate;
    private Map<String, List<String>> urlDownload;

}
