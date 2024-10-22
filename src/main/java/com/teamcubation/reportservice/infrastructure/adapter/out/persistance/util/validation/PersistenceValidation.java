package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.util.validation;

public class PersistenceValidation {
    public static final String INVALID_DATA = "Invalid data";

    public static void validateObjetNotNull(Object object) {
        if (object == null) throw new RuntimeException(INVALID_DATA);
    }
}
