package com.griddynamics.backoffice.exception;

import org.springframework.http.HttpStatus;

public class PaginationException extends BackofficeException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    public PaginationException() {
        super(HTTP_STATUS);
    }

    public PaginationException(String message) {
        super(message, HTTP_STATUS);
    }

    public PaginationException(String message, Throwable cause) {
        super(message, cause, HTTP_STATUS);
    }

    public PaginationException(Throwable cause) {
        super(cause, HTTP_STATUS);
    }
}
