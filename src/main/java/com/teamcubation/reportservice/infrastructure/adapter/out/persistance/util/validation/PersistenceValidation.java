package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.util.validation;

import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.exception.reportException.InvalidObject;

public class PersistenceValidation {
    public static final String INVALID_DATA= "Invalid data";

    public static void validateObjetNotNull(Object object) throws InvalidObject {
        if (object == null) throw new InvalidObject(INVALID_DATA);
    }
}
