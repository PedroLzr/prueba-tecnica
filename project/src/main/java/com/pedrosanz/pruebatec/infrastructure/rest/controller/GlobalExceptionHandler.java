package com.pedrosanz.pruebatec.infrastructure.rest.controller;

import com.pedrosanz.pruebatec.application.exception.PriceNotFoundException;
import com.pedrosanz.pruebatec.infrastructure.rest.dto.ErrorResponseDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePriceNotFound(PriceNotFoundException ex) {
        log.error("PriceNotFound manejado: {} - Mensaje: {}", ex.getClass().getSimpleName(), ex.getMessage());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(ConstraintViolationException ex) {
        log.error("ConstraintViolation manejado: {}", ex.getMessage());

        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .findFirst()
                .orElse("Error de validación");

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(errorMessage);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        log.error("MissingServletRequestParameterException manejado: {}", ex.getMessage());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getParameterName() + ": must not be null");

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.error("MethodArgumentTypeMismatchException manejado: {}", ex.getMessage());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getName() + ": must be a valid number");

        return ResponseEntity.badRequest().body(errorResponse);
    }
}