package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(collection = "Reports")
public class ReportEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String title;
    private String userEmail;
    private String downloadUrlPdf;
    private String downloadUrlCsv;
    private LocalDateTime creationDate;
    private byte[] content;
}
