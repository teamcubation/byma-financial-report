package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.util.validation;

import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.exception.reportException.InvalidObjectException;

public class PersistenceValidation {
    public static final String INVALID_DATA= "Invalid data";

    public static void validateObjetNotNull(Object object) throws InvalidObjectException {
        if (object == null) throw new InvalidObjectException(INVALID_DATA);
    }
}
