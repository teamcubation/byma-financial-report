package com.teamcubation.reportservice.application.service;

import com.teamcubation.reportservice.domain.model.CategoryReport;
import com.teamcubation.reportservice.domain.model.Report;

import java.time.LocalDate;

public class reportService {
    public Report generateReport(Report report) {
        // TODO: conectar con microservicios para obtener lista de instrumentos
        String listFinantialInstrument = null;

        if (condition(CategoryReport.NAME, report)) {
            filterByName(filterValue(report, CategoryReport.NAME), listFinantialInstrument);
        }
        if (condition(CategoryReport.PRICE_MIN, report)) {
            filterByPriceMin(filterValue(report, CategoryReport.PRICE_MIN), listFinantialInstrument);
        }
        if (condition(CategoryReport.PRICE_MAX, report)) {
            filterByPriceMax(filterValue(report, CategoryReport.PRICE_MAX), listFinantialInstrument);
        }
        if (condition(CategoryReport.CREATION_DATE, report)) {
            filterByCreationDate(filterValue(report, CategoryReport.CREATION_DATE), listFinantialInstrument);
        }
        if (condition(CategoryReport.TYPE, report)) {
            filterByType(filterValue(report, CategoryReport.TYPE), listFinantialInstrument);
        }
        if (condition(CategoryReport.INTERES_RATE, report)) {
            filterByInterestRate(filterValue(report, CategoryReport.INTERES_RATE), listFinantialInstrument);
        }
        if (condition(CategoryReport.DIVIDEND, report)) {
            filterByDividend(filterValue(report, CategoryReport.DIVIDEND), listFinantialInstrument);
        }
        Report newReport = new Report();
        return report;
    }

    private void filterByName(String name, String listFinantialInstrument) {
    }
    private void filterByPriceMin(String priceMin, String listFinantialInstrument) {
        double price = Double.parseDouble(priceMin);
    }

    private void filterByPriceMax(String priceMax, String listFinantialInstrument) {
        double price = Double.parseDouble(priceMax);
    }

    private void filterByCreationDate(String creationDate, String listFinantialInstrument) {
        LocalDate date = LocalDate.parse(creationDate);
    }

    private void filterByType(String type, String listFinantialInstrument) {

    }

    private void filterByInterestRate(String interestRate, String listFinantialInstrument) {
        double interestRateValue = Double.parseDouble(interestRate);
    }

    private void filterByDividend(String dividend, String listFinantialInstrument) {
        double dividendValue = Double.parseDouble(dividend);
    }


    private boolean condition(CategoryReport categoryReport, Report report) {
        return report.getFilters().stream().anyMatch(filter -> filter.getCategory() == categoryReport);
    }

    private String filterValue(Report report, CategoryReport category) {
        return report.getFilters().stream().filter(elem -> elem.equals(category)).findFirst().get().getValue();
    }
}