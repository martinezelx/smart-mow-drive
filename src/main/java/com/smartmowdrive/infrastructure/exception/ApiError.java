package com.smartmowdrive.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiError {

    private String exception;
    private HttpStatus status;
    private String message;
}
