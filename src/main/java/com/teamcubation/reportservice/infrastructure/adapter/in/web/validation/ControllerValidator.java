package com.teamcubation.reportservice.infrastructure.adapter.in.web.validation;

public class ControllerValidator {

    public static boolean isNull(Object ...objects) {
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }
}
