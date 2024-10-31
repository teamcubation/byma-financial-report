package com.teamcubation.reportservice.reportOutAdapter;

import com.teamcubation.reportservice.domain.customexceptions.report.ReportNotFoundException;
import com.teamcubation.reportservice.domain.model.report.Report;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.report.ReportOutAdapter;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity.ReportEntity;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.exception.reportException.InvalidObjectException;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.mapper.ReportPersistenceMapper;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReportOutAdapterTest {
    public static final int EXPECTED_NUMBER_0 = 0;
    @InjectMocks
    private ReportOutAdapter reportOutAdapter;
    @Mock
    private ReportRepository reportRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public static final byte[] MOCK_BYTE_ARRAY_RESULT = {1, 2, 2, 3, 4, 4};
    public static final String TEST_GMAIL = "test@gmail";
    public static final String INVALID = "invalid";
    public static final String REPORT_A = "ReportA";
    public static final String ID_1 = "1";
    public static final String REPORT_B = "ReportB";
    public static final String ID_2 = "2";

    Report mockReport() {
        Report report = new Report();
        report.setId(ID_1);
        report.setTitle(REPORT_A);
        report.setUserEmail(TEST_GMAIL);
        report.setCreationDate(LocalDateTime.now());
        report.setDownloadUrl(null);
        report.setContent(MOCK_BYTE_ARRAY_RESULT);
        return report;
    }

    Report mockReport2() {
        Report report = new Report();
        report.setId(ID_2);
        report.setTitle(REPORT_B);
        report.setUserEmail(TEST_GMAIL);
        report.setCreationDate(LocalDateTime.now());
        report.setDownloadUrl(null);
        report.setContent(MOCK_BYTE_ARRAY_RESULT);
        return report;
    }

    @Test
    void whenSave_thenReturnReportTest() throws InvalidObjectException

    {
        Report report = new Report();
        ReportEntity reportEntity = new ReportEntity();
        when(reportRepository.save(any())).thenReturn(reportEntity);
        ReportEntity result = reportOutAdapter.save(report);
        assertEquals(reportEntity, result);
        verify(reportRepository).save(any());
    }


    @Test
    void whenFindById_thenReturnReportTest() throws InvalidObjectException {
        ReportEntity report = ReportPersistenceMapper.reportModelToReportEntity(mockReport());
        when(reportRepository.findById(ID_1)).thenReturn(Optional.of(report));
        ReportEntity result = reportOutAdapter.findById(ID_1);
        assertEquals(report, result);
        verify(reportRepository).findById(ID_1);
    }

    @Test
    void whenGetAll_thenReturnReportListTest() throws InvalidObjectException {
        List<ReportEntity> reportList = List.of(ReportPersistenceMapper.reportModelToReportEntity(mockReport()), ReportPersistenceMapper.reportModelToReportEntity(mockReport2()));
        when(reportRepository.findAll()).thenReturn(reportList);
        List<ReportEntity> result = reportOutAdapter.getAll();
        assertEquals(reportList, result);
        verify(reportRepository).findAll();
    }

    @Test
    void whenFindByUserEmail_thenReturnReportListTest() throws InvalidObjectException {
        List<ReportEntity> reportList = List.of(ReportPersistenceMapper.reportModelToReportEntity(mockReport()), ReportPersistenceMapper.reportModelToReportEntity(mockReport2()));
        when(reportRepository.findByUserEmail(TEST_GMAIL)).thenReturn(reportList);
        List<ReportEntity> result = reportOutAdapter.findByUserEmail(TEST_GMAIL);
        assertEquals(reportList, result);
        verify(reportRepository).findByUserEmail(TEST_GMAIL);
    }
    @Test
    void whenFindByUserEmailWithInvalidEmail_thenReturnEmptyListTest() {
        List<ReportEntity> result = reportOutAdapter.findByUserEmail(INVALID);
        assertEquals(EXPECTED_NUMBER_0, result.size());
        verify(reportRepository).findByUserEmail(INVALID);
    }
}
