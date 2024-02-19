package com.smartmowdrive.application.exceptions;

public class DuplicateMowerPositionException extends RuntimeException {
  public DuplicateMowerPositionException(String message) {
    super(message);
  }
}
