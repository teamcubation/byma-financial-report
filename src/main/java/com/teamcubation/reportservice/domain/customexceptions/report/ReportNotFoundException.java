package com.teamcubation.reportservice.domain.customexceptions.report;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(String message) {
        super(message);
    }
}
