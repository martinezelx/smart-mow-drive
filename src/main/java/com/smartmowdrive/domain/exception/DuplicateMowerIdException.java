package com.smartmowdrive.domain.exception;

public class DuplicateMowerIdException extends RuntimeException {
    public DuplicateMowerIdException(String message) {
        super(message);
    }
}
