package com.teamcubation.reportservice.application.port.in;

import java.io.IOException;

public interface ReportInPort {
    byte[] generatePdf() throws IOException;
    byte[] generateCsv() throws IOException;
}
