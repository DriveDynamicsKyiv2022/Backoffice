package com.griddynamics.backoffice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BackofficeException extends RuntimeException {
    private final HttpStatus httpStatus;

    public BackofficeException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public BackofficeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public BackofficeException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public BackofficeException(Throwable cause, HttpStatus httpStatus) {
        super(cause);
        this.httpStatus = httpStatus;
    }
}
