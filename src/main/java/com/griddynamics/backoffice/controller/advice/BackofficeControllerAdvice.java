package com.griddynamics.backoffice.controller.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BackofficeControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleArgumentConflict(IllegalArgumentException e, WebRequest request) {
        return handleExceptionInternal(e, e, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

//    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeError(RuntimeException e, WebRequest request) {
        return handleExceptionInternal(e, e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
