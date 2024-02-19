package com.smartmowdrive.domain.commons.exceptions;

public class InvalidPositionException extends RuntimeException {
  public InvalidPositionException(String message) {
    super(message);
  }
}
