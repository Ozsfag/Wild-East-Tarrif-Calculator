package ru.fastdelivery.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.fastdelivery.presentation.model.response.ApiError;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException e) {
    ApiError apiError = ApiError.badRequest(e.getMessage());
    return new ResponseEntity<>(apiError, apiError.httpStatus());
  }
}
