package com.teamcubation.reportservice.application.port.in;

import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.exception.reportException.InvalidObjectException;

import java.io.IOException;
import java.util.List;

public interface ReportInPort {
    byte[] generateFile(String typeFile, String typeInstrument) throws IOException, InvalidObjectException;
    byte[] generatePdf(String typeInstrument) throws IOException;
    byte[] generateCsv(String typeInstrument) throws IOException;
    byte[] downloadFile(String id) throws IOException;
    List<Report> getAllReports() throws InvalidObjectException;
    List<Report> findByUserEmail() throws InvalidObjectException;
}
