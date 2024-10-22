package com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.validation;

public class PersistanceValidation {

    public static boolean isNull(Object ...objects) {
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }
}
