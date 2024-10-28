package com.teamcubation.reportservice.serviceReport;

import com.teamcubation.reportservice.application.port.out.ConnectionOutPort;
import com.teamcubation.reportservice.application.port.out.ReportOutPort;
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
    public static final byte[] BYTE_ARRAY_RESULT = {1, 2, 2, 3, 4, 4};
    public static final String TEST_GMAIL = "test@gmail";
    public static final String INVALID = "invalid";
    public static final String BONDS = "bonds";
    public static final String STOCKS = "stocks";
    public static final String CSV = "csv";
    public static final String PDF = "pdf";
    public static final String URLCSV = "urlcsv";
    public static final String URLPDF = "urlpdf";
    public static final String REPORT_A = "ReportA";
    public static final String ID_1 = "1";
    public static final String REPORT_B = "ReportB";
    public static final String ID_2 = "2";
    public static final double PRICE_NUMBER_10 = 10.0;
    public static final double INTEREST_RATE_NUMBER_10 = 10.0;
    public static final String BOND_TEST_NAME = "bond test";
    public static final double DIVIDEND_NUMBER_10 = 10.0;
    public static final String STOCK_TEST_NAME = "stock test";
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
        stock.setId(Long.parseLong(ID_1));
        stock.setName(STOCK_TEST_NAME);
        stock.setDividend(DIVIDEND_NUMBER_10);
        stock.setPrice(PRICE_NUMBER_10);
        stocks.add(stock);
        return stocks;
    }
    List<BonoDto>mockBonds(){
        List <BonoDto> bonds = new ArrayList<>();
        BonoDto bono = new BonoDto();
        bono.setCreationDate(LocalDate.now());
        bono.setId(Long.parseLong(ID_1));
        bono.setName(BOND_TEST_NAME);
        bono.setInterestRate(INTEREST_RATE_NUMBER_10);
        bono.setPrice(PRICE_NUMBER_10);
        bonds.add(bono);
        return bonds;
    }
    private static Report mockReportB() {
        Report reportB = new Report();
        reportB.setId(ID_2);
        reportB.setTitle(REPORT_B);
        reportB.setUserEmail(TEST_GMAIL);
        reportB.setCreationDate(LocalDateTime.now());
        reportB.setDownloadUrlPdf(URLPDF);
        reportB.setDownloadUrlCsv(URLCSV);
        reportB.setContent(new byte[]{});
        return reportB;
    }
    private static Report mockReportA() {
        Report reportA = new Report();
        reportA.setId(ID_1);
        reportA.setTitle(REPORT_A);
        reportA.setUserEmail(TEST_GMAIL);
        reportA.setCreationDate(LocalDateTime.now());
        reportA.setDownloadUrlPdf(URLPDF);
        reportA.setDownloadUrlCsv(URLCSV);
        reportA.setContent(new byte[]{});
        return reportA;
    }
    @Test
    void whenGenerateFileWithPdfParams_thenReturnPdfFileTest() throws IOException {
        byte[] byteArrayResult = new byte[]{1,2,2,3,4,4};
        String typeFile = PDF;
        String typeInstrument = STOCKS;
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
        byte[] byteArrayResult = BYTE_ARRAY_RESULT;
        String typeFile = CSV;
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
        String typeFile = INVALID;
        String typeInstrument = STOCKS;
        assertThrows(InvalidTypeFyleException.class, () -> reportService.generateFile(typeFile, typeInstrument));
    }

    @Test
    void whenGeneratePdfWithStocksParams_thenReturnListOfStocksTest() throws IOException {
        String typeInstrument = STOCKS;
        byte[] byteArrayResult = BYTE_ARRAY_RESULT;
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
        String typeInstrument = BONDS;
        byte[] byteArrayResult = BYTE_ARRAY_RESULT;
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
        byte[] byteArrayResult = BYTE_ARRAY_RESULT;
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
        String typeInstrument = INVALID;
        assertThrows(InvalidInstrumentException.class, () -> reportService.generatePdf(typeInstrument));
    }
    @Test
    void whenGenerateCsvWithStocksParams_thenReturnListOfStocksTest() throws IOException {
        String typeInstrument = STOCKS;
        byte[] byteArrayResult = BYTE_ARRAY_RESULT;
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
        String typeInstrument = BONDS;
        byte[] byteArrayResult = BYTE_ARRAY_RESULT;
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
        byte[] byteArrayResult = BYTE_ARRAY_RESULT;
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
        String typeInstrument = INVALID;
        assertThrows(InvalidInstrumentException.class, () -> reportService.generateCsv(typeInstrument));
    }

    @Test
    void whenGetAllReports_returnNoDataReportTest() {
        List<ReportEntity> reportList = new ArrayList<>();
        when(reportOutPort.getAll()).thenReturn(reportList);
        List<Report> result = reportService.getAllReports();
        assertEquals(result.size(), reportList.size());
    }


    @Test
    void whenGetAllReports_return2ReportsTest() {
        Report reportA = mockReportA();
        reportOutPort.save(reportA);

        Report reportB = mockReportB();
        reportOutPort.save(reportB);

        List<ReportEntity> reportList = new ArrayList<>();
        reportList.add(ReportPersistenceMapper.reportModelToReportEntity(reportA));
        reportList.add(ReportPersistenceMapper.reportModelToReportEntity(reportB));
        when(reportOutPort.getAll()).thenReturn(reportList);
        List<Report> result = reportService.getAllReports();
        assertEquals(result.size(), reportList.size());
    }



    @Test
    void whenFindByEmailWithValidEmail_thenReturnListOfReportsTest(){
        Report reportA = mockReportA();
        reportOutPort.save(reportA);

        Report reportB = mockReportB();
        reportOutPort.save(reportB);

        List<ReportEntity> reportList = new ArrayList<>();
        reportList.add(ReportPersistenceMapper.reportModelToReportEntity(reportA));
        reportList.add(ReportPersistenceMapper.reportModelToReportEntity(reportB));
        when(reportOutPort.findByUserEmail(TEST_GMAIL)).thenReturn(reportList);
        List<Report> result = reportService.findByUserEmail(TEST_GMAIL);
        assertEquals(result.size(), reportList.size());
    }
}