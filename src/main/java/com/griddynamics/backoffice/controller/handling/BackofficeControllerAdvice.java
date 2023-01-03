package com.griddynamics.backoffice.controller.handling;

import com.griddynamics.backoffice.exception.BackofficeException;
import com.griddynamics.backoffice.exception.DatabaseException;
import com.griddynamics.backoffice.exception.PaginationException;
import com.griddynamics.backoffice.exception.ResourceNotFoundException;
import com.griddynamics.backoffice.exception.ServerError;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ControllerAdvice
public class BackofficeControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleArgumentConflict(RuntimeException e, WebRequest request) {
        ServerError serverError = buildServerError(HttpStatus.BAD_REQUEST, e, request);
        return buildResponseEntity(serverError);
    }

    @ExceptionHandler(value = {DatabaseException.class, ResourceNotFoundException.class, PaginationException.class})
    protected ResponseEntity<Object> handleBackofficeException(BackofficeException e, WebRequest request) {
        ServerError serverError = buildServerError(e, request);
        return buildResponseEntity(serverError);
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Exception exception = new Exception(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        ServerError serverError = buildServerError(status, exception, request);
        return buildResponseEntity(serverError);
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServerError serverError = buildServerError(status, ex, request);
        return buildResponseEntity(serverError);
    }

    private ServerError buildServerError(BackofficeException e, WebRequest webRequest) {
        return buildServerError(e.getHttpStatus(), e, webRequest);
    }

    private ServerError buildServerError(HttpStatus httpStatus, Exception e, WebRequest webRequest) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) webRequest;
        return ServerError.builder()
                .httpStatus(httpStatus)
                .code(httpStatus.value())
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .message(e.getMessage())
                .path(servletWebRequest.getRequest().getRequestURI())
                .build();
    }

    private ResponseEntity<Object> buildResponseEntity(ServerError serverError) {
        return new ResponseEntity<>(serverError, serverError.getHttpStatus());
    }
}
