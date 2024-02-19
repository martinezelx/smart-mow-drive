package com.smartmowdrive.application.exceptions;

public class DuplicateMowerIdException extends RuntimeException {
  public DuplicateMowerIdException(String message) {
    super(message);
  }
}
