package com.teamcubation.reportservice.controllerReport;

import com.teamcubation.reportservice.application.port.in.ReportInPort;
import com.teamcubation.reportservice.domain.customexceptions.report.InvalidInstrumentException;
import com.teamcubation.reportservice.domain.customexceptions.report.InvalidTypeFyleException;
import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl.ReportController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ControllerTest {

    public static final byte[] MOCK_BYTE_ARRAY_RESULT = {1, 2, 2, 3, 4, 4};
    public static final String PDF = "pdf";
    public static final String CSV = "csv";
    public static final String INVALID = "invalid";
    public static final String APPLICATION_CSV_PATH = "application/csv";
    public static final String ATTACHMENT_FILENAME_REPORT_CSV = "attachment; filename=report.csv";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String ATTACHMENT_FILENAME_REPORT_PDF = "attachment; filename=report.pdf";
    public static final String APPLICATION_PDF_PATH = "application/pdf";
    public static final String ID_1 = "1";
    public static final String REPORT_1 = "report1";
    public static final String USER_1 = "user1";
    public static final String URLCSV_1 = "urlcsv1";
    public static final String URLPDF_1 = "urlpdf1";
    public static final String ID_2 = "2";
    public static final String REPORT_2 = "report2";
    public static final String USER_2 = "user2";
    public static final String URLCSV_2 = "urlcsv2";
    public static final String URLPDF_2 = "urlpdf2";

    @InjectMocks
    private ReportController reportController;
    @Mock
    ReportInPort reportInPort;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

    }

    List<Report> mockListReports() {
        Report report1 = new Report();
        report1.setId(ID_1);
        report1.setTitle(REPORT_1);
        report1.setUserEmail(USER_1);
        report1.setDownloadUrl(null);
        report1.setContent(MOCK_BYTE_ARRAY_RESULT);
        report1.setCreationDate(null);
        Report report2 = new Report();
        report2.setId(ID_2);
        report2.setTitle(REPORT_2);
        report2.setUserEmail(USER_2);
        report2.setDownloadUrl(null);
        report2.setContent(MOCK_BYTE_ARRAY_RESULT);
        report2.setCreationDate(null);
        return List.of(report1, report2);
    }

    @Test
    void whenGenerateFileWithNoParams_thenReturnReportFileWithHeaders() throws Exception {
        when(reportInPort.generateFile(PDF, null)).thenReturn(MOCK_BYTE_ARRAY_RESULT);
        ResponseEntity<byte[]> result = reportController.downloadFile(PDF, null);
        assertEquals(result.getBody(), MOCK_BYTE_ARRAY_RESULT);
        assertEquals(APPLICATION_PDF_PATH, result.getHeaders().getContentType().toString());
        assertEquals(ATTACHMENT_FILENAME_REPORT_PDF, result.getHeaders().getFirst(CONTENT_DISPOSITION));
    }

    @Test
    void whenGenerateFileWithCsvParams_thenReturnCsvFile() throws Exception {
        when(reportInPort.generateFile(CSV, null)).thenReturn(MOCK_BYTE_ARRAY_RESULT);
        ResponseEntity<byte[]> result = reportController.downloadFile(CSV, null);
        assertEquals(result.getBody(), MOCK_BYTE_ARRAY_RESULT);
        assertEquals(APPLICATION_CSV_PATH, result.getHeaders().getContentType().toString());
        assertEquals(ATTACHMENT_FILENAME_REPORT_CSV, result.getHeaders().getFirst(CONTENT_DISPOSITION));
    }

    @Test
    void whenGenerateFileWithInvalidFileTypeParams_thenReturnInvalidTypeFyleException() throws Exception {
        when(reportInPort.generateFile(INVALID, null)).thenThrow(InvalidTypeFyleException.class);
        assertThrows(InvalidTypeFyleException.class, () -> reportController.downloadFile(INVALID, null));
    }

    @Test
    void whenGenerateFileWithInvalidInstrumentParams_thenReturnInvalidInstrumentException() throws Exception {
        when(reportInPort.generateFile(PDF, INVALID)).thenThrow(InvalidInstrumentException.class);
        assertThrows(InvalidInstrumentException.class, () -> reportController.downloadFile(PDF, INVALID));
    }

    @Test
    void whenGenerateFileThrowsIOException_thenHandleException() throws Exception {
        when(reportInPort.generateFile(PDF, null)).thenThrow(IOException.class);
        assertThrows(IOException.class, () -> reportController.downloadFile(PDF, null));
    }

    @Test
    void whenGetAllReports_thenReturnAll2Reports() {
        when(reportInPort.getAllReports()).thenReturn(mockListReports());
        ResponseEntity<List<Report>> result = reportController.getAllReports();
        assertEquals(mockListReports(), result.getBody());
    }
}
