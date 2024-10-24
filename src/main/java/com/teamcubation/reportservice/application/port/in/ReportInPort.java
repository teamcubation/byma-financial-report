package com.teamcubation.reportservice.application.port.in;

import com.teamcubation.reportservice.domain.model.report.Report;

import java.io.IOException;
import java.util.List;

public interface ReportInPort {
    byte[] generateFile(String typeFile, String typeInstrument) throws IOException;
    byte[] generatePdf(String typeInstrument) throws IOException;
    byte[] generateCsv(String typeInstrument) throws IOException;
    List<Report> getAllReports();
}
