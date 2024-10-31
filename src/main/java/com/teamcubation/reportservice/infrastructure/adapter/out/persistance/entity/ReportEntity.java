package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Reports")
public class ReportEntity {
    @Id
    private String id;
    private String title;
    @Indexed(unique = true)
    private String userEmail;
    private List<String> downloadUrl;
    private LocalDateTime creationDate;
    private byte[] content;
}
