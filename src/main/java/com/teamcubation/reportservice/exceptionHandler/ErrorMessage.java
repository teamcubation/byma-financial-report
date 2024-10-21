package com.teamcubation.reportservice.exceptionHandler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorMessage {

    private final String message;
    private final int status;
    private final String path;
    private final String method;
    @Builder.Default
    private final LocalDateTime timestamp = LocalDateTime.now();



}
