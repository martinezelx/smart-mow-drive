package com.smartmowdrive.domain.exception;

public class DuplicateMowerPositionException extends RuntimeException {
    public DuplicateMowerPositionException(String message) {
        super(message);
    }
}
