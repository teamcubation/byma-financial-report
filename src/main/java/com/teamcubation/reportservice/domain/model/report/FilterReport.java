package com.teamcubation.reportservice.domain.model.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterReport {
    private CategoryReport category;
    private String value;
}
