package com.teamcubation.reportservice.serviceReport;

import com.teamcubation.reportservice.application.port.out.ConnectionOutPort;
import com.teamcubation.reportservice.application.port.out.ReportOutPort;
import com.teamcubation.reportservice.application.port.out.UserOutPort;
import com.teamcubation.reportservice.application.service.ReportService;
import com.teamcubation.reportservice.application.service.generatorfile.GeneratorCsv;
import com.teamcubation.reportservice.application.service.generatorfile.GeneratorPdf;
import com.teamcubation.reportservice.domain.customexceptions.report.InvalidInstrumentException;
import com.teamcubation.reportservice.domain.customexceptions.report.InvalidTypeFyleException;
import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.BonoDto;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.StockDto;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.ReportEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.ReportPersistenceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class ServiceTest {
    @InjectMocks
    private ReportService reportService;

    @Mock
    private ReportOutPort reportOutPort;
    @Mock
    private ConnectionOutPort connectionOutPort;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    List<StockDto>mockStocks(){
        List <StockDto> stocks = new ArrayList<>();
        StockDto stock = new StockDto();
        stock.setCreationDate(LocalDate.now());
        stock.setId(1L);
        stock.setName("test");
        stock.setDividend(10.0);
        stock.setPrice(10.0);
        stocks.add(stock);
        return stocks;
    }
    List<BonoDto>mockBonds(){
        List <BonoDto> bonds = new ArrayList<>();
        BonoDto bono = new BonoDto();
        bono.setCreationDate(LocalDate.now());
        bono.setId(1L);
        bono.setName("test");
        bono.setInterestRate(10.0);
        bono.setPrice(10.0);
        bonds.add(bono);
        return bonds;
    }
    @Test
    void whenGenerateFileWithPdfParams_thenReturnPdfFileTest() throws IOException {
        byte[] byteArrayResult = new byte[]{1,2,2,3,4,4};
        String typeFile = "pdf";
        String typeInstrument = "stocks";
        when(connectionOutPort.getAllStocks()).thenReturn(mockStocks());
        when(reportOutPort.save(any(Report.class))).thenReturn(ReportPersistenceMapper.reportModelToReportEntity(new Report()));
        try (MockedStatic<GeneratorPdf> mockedGeneratorPdf = mockStatic(GeneratorPdf.class)) {
            mockedGeneratorPdf.when(() -> GeneratorPdf.generatePdfContent(any(), any()))
                    .thenReturn(byteArrayResult);
            byte[] result = reportService.generateFile(typeFile, typeInstrument);
            assertEquals(byteArrayResult, result);
        }
    }
    @Test
    void whenGenerateFileWithCsvParams_thenReturnCsvFileTest() throws IOException {
        byte[] byteArrayResult = new byte[]{1,2,2,3,4,4};
        String typeFile = "csv";
        String typeInstrument = null;
        when(connectionOutPort.getAllBonds()).thenReturn(mockBonds());
        when(connectionOutPort.getAllStocks()).thenReturn(mockStocks());
        when(reportOutPort.save(any(Report.class))).thenReturn(ReportPersistenceMapper.reportModelToReportEntity(new Report()));
        try (MockedStatic<GeneratorPdf> mockedGeneratorPdf = mockStatic(GeneratorPdf.class)) {
            mockedGeneratorPdf.when(() -> GeneratorPdf.generatePdfContent(any(), any()))
                    .thenReturn(byteArrayResult);
            byte[] result = reportService.generateFile(typeFile, typeInstrument);
            assertEquals(byteArrayResult, result);
        }
    }
    @Test
    void whenGenerateFileWithInvalidParams_thenReturnInvalidTypeExceptionTest() {
        String typeFile = "invalid";
        String typeInstrument = "stocks";
        assertThrows(InvalidTypeFyleException.class, () -> reportService.generateFile(typeFile, typeInstrument));
    }

    @Test
    void whenGeneratePdfWithStocksParams_thenReturnListOfStocksTest() throws IOException {
        String typeInstrument = "stocks";
        byte[] byteArrayResult = new byte[]{1,2,2,3,4,4};
        when(connectionOutPort.getAllStocks()).thenReturn(mockStocks());
        try (MockedStatic<GeneratorPdf> mockedGeneratorPdf = mockStatic(GeneratorPdf.class)) {
            mockedGeneratorPdf.when(() -> GeneratorPdf.generatePdfContent(any(), any()))
                    .thenReturn(byteArrayResult);
            byte[] result = reportService.generatePdf(typeInstrument);
            assertEquals(byteArrayResult, result);
        }
    }
    @Test
    void whenGeneratePdfWithBondsParams_thenReturnListOfBondsTest() throws IOException {
        String typeInstrument = "bonds";
        byte[] byteArrayResult = new byte[]{1,2,2,3,4,4};
        when(connectionOutPort.getAllBonds()).thenReturn(mockBonds());
        try (MockedStatic<GeneratorPdf> mockedGeneratorPdf = mockStatic(GeneratorPdf.class)) {
            mockedGeneratorPdf.when(() -> GeneratorPdf.generatePdfContent(any(), any()))
                    .thenReturn(byteArrayResult);
            byte[] result = reportService.generatePdf(typeInstrument);
            assertEquals(byteArrayResult, result);
        }
    }
    @Test
    void generatePdfWithNullParams_thenReturnInvalidTypeExceptionTest() throws IOException {
        String typeInstrument = null;
        byte[] byteArrayResult = new byte[]{1,2,2,3,4,4};
        when(connectionOutPort.getAllBonds()).thenReturn(mockBonds());
        when(connectionOutPort.getAllStocks()).thenReturn(mockStocks());
        try (MockedStatic<GeneratorPdf> mockedGeneratorPdf = mockStatic(GeneratorPdf.class)) {
            mockedGeneratorPdf.when(() -> GeneratorPdf.generatePdfContent(any(), any()))
                    .thenReturn(byteArrayResult);
            byte[] result = reportService.generatePdf(typeInstrument);
            assertEquals(byteArrayResult, result);
        }
    }
    @Test
    void whenGeneratePdfWithInvalidParams_thenReturnInvalidTypeExceptionTest() {
        String typeInstrument = "invalid";
        assertThrows(InvalidInstrumentException.class, () -> reportService.generatePdf(typeInstrument));
    }
    @Test
    void whenGenerateCsvWithStocksParams_thenReturnListOfStocksTest() throws IOException {
        String typeInstrument = "stocks";
        byte[] byteArrayResult = new byte[]{1,2,2,3,4,4};
        when(connectionOutPort.getAllStocks()).thenReturn(mockStocks());
        try (MockedStatic<GeneratorCsv> mockedGeneratorCsv = mockStatic(GeneratorCsv.class)) {
            mockedGeneratorCsv.when(() -> GeneratorCsv.generateCsv(any(), any()))
                    .thenReturn(byteArrayResult);
            byte[] result = reportService.generateCsv(typeInstrument);
            assertEquals(byteArrayResult, result);
        }
    }
    @Test
    void whenGenerateCsvWithBondsParams_thenReturnListOfBondsTest() throws IOException {
        String typeInstrument = "bonds";
        byte[] byteArrayResult = new byte[]{1,2,2,3,4,4};
        when(connectionOutPort.getAllStocks()).thenReturn(mockStocks());
        try (MockedStatic<GeneratorCsv> mockedGeneratorCsv = mockStatic(GeneratorCsv.class)) {
            mockedGeneratorCsv.when(() -> GeneratorCsv.generateCsv(any(), any()))
                    .thenReturn(byteArrayResult);
            byte[] result = reportService.generateCsv(typeInstrument);
            assertEquals(byteArrayResult, result);
        }
    }
    @Test
    void whenGenerateCsvWithNullParams_thenReturnInvalidTypeExceptionTest() throws IOException {
        String typeInstrument = null;
        byte[] byteArrayResult = new byte[]{1,2,2,3,4,4};
        when(connectionOutPort.getAllBonds()).thenReturn(mockBonds());
        when(connectionOutPort.getAllStocks()).thenReturn(mockStocks());
        try (MockedStatic<GeneratorCsv> mockedGeneratorCsv = mockStatic(GeneratorCsv.class)) {
            mockedGeneratorCsv.when(() -> GeneratorCsv.generateCsv(any(), any()))
                    .thenReturn(byteArrayResult);
            byte[] result = reportService.generateCsv(typeInstrument);
            assertEquals(byteArrayResult, result);
        }
    }
    @Test
    void whenGenerateCsvWithInvalidParams_thenReturnInvalidTypeExceptionTest() {
        String typeInstrument = "invalid";
        assertThrows(InvalidInstrumentException.class, () -> reportService.generateCsv(typeInstrument));
    }

    @Test
    void whenGetAllReports_returnEmptyListTest() {
        List<ReportEntity> reportList = new ArrayList<>();
        when(reportOutPort.getAll()).thenReturn(reportList);
        List<Report> result = reportService.getAllReports();
        assertEquals(result.size(), reportList.size());
    }


    @Test
    void whenGetAllReports_return2ReportsTest() {

        Report reportA = new Report();
        reportA.setId("1");
        reportA.setTitle("Report A");
        reportA.setUserEmail("test@gmail");
        reportA.setCreationDate(LocalDateTime.now());
        reportA.setDownloadUrlPdf("urlpdf");
        reportA.setDownloadUrlCsv("urlcsv");
        reportA.setContent(new byte[]{});
        reportOutPort.save(reportA);

        Report reportB = new Report();
        reportB.setId("2");
        reportB.setTitle("Report B");
        reportB.setUserEmail("test@gmail");
        reportB.setCreationDate(LocalDateTime.now());
        reportB.setDownloadUrlPdf("urlpdf");
        reportB.setDownloadUrlCsv("urlcsv");
        reportB.setContent(new byte[]{});
        reportOutPort.save(reportB);

        List<ReportEntity> reportList = new ArrayList<>();
        reportList.add(ReportPersistenceMapper.reportModelToReportEntity(reportA));
        reportList.add(ReportPersistenceMapper.reportModelToReportEntity(reportB));
        when(reportOutPort.getAll()).thenReturn(reportList);
        List<Report> result = reportService.getAllReports();
        assertEquals(result.size(), reportList.size());
    }
    @Test
    void whenFindByEmailWithValidEmail_thenReturnListOfReportsTest() throws Exception{
        Report reportA = new Report();
        reportA.setId("1");
        reportA.setTitle("Report A");
        reportA.setUserEmail("test@gmail");
        reportA.setCreationDate(LocalDateTime.now());
        reportA.setDownloadUrlPdf("urlpdf");
        reportA.setDownloadUrlCsv("urlcsv");
        reportA.setContent(new byte[]{});
        reportOutPort.save(reportA);

        Report reportB = new Report();
        reportB.setId("2");
        reportB.setTitle("Report B");
        reportB.setUserEmail("test@gmail");
        reportB.setCreationDate(LocalDateTime.now());
        reportB.setDownloadUrlPdf("urlpdf");
        reportB.setDownloadUrlCsv("urlcsv");
        reportB.setContent(new byte[]{});
        reportOutPort.save(reportB);

        List<ReportEntity> reportList = new ArrayList<>();
        reportList.add(ReportPersistenceMapper.reportModelToReportEntity(reportA));
        reportList.add(ReportPersistenceMapper.reportModelToReportEntity(reportB));
        when(reportOutPort.findByUserEmail("test@gmail")).thenReturn(reportList);
        List<Report> result = reportService.findByUserEmail("test@gmail");
        assertEquals(result.size(), reportList.size());
    }
}