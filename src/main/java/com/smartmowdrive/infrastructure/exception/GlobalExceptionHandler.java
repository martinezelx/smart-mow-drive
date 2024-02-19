package com.smartmowdrive.infrastructure.exception;

import com.smartmowdrive.domain.constants.ErrorMessages;
import com.smartmowdrive.domain.exception.*;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicateMowerPositionException.class)
  public ResponseEntity<Object> handleDuplicateMowerPositionException(
      DuplicateMowerPositionException ex) {
    ApiError apiError =
        new ApiError(ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(InvalidDimensionsException.class)
  public ResponseEntity<Object> handleInvalidDimensionsException(InvalidDimensionsException ex) {
    ApiError apiError =
        new ApiError(ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(InvalidInstructionException.class)
  public ResponseEntity<Object> handleInvalidInstructionException(InvalidInstructionException ex) {
    ApiError apiError =
        new ApiError(ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(InvalidPositionException.class)
  public ResponseEntity<Object> handleInvalidPositionException(InvalidPositionException ex) {
    ApiError apiError =
        new ApiError(ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(OutOfBoundsException.class)
  public ResponseEntity<Object> handleOutOfBoundsException(OutOfBoundsException ex) {
    ApiError apiError =
        new ApiError(ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(DuplicateMowerIdException.class)
  public ResponseEntity<Object> handleDuplicateMowerIdException(DuplicateMowerIdException ex) {
    ApiError apiError =
        new ApiError(ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    ApiError apiError =
        new ApiError(ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, errors.toString());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Object> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex) {
    ApiError apiError =
        new ApiError(ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericException(Exception ex) {
    ApiError apiError =
        new ApiError(
            ex.getClass().getSimpleName(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorMessages.GENERIC_ERROR);
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
    ApiError apiError =
        new ApiError(
            ex.getClass().getSimpleName(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorMessages.NULL_POINTER);
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
