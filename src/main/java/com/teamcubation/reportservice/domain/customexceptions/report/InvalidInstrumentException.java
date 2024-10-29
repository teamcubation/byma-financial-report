package com.teamcubation.reportservice.domain.customexceptions.report;

public class InvalidInstrumentException extends RuntimeException{
    public InvalidInstrumentException(String message) {
        super(message);
    }

}
