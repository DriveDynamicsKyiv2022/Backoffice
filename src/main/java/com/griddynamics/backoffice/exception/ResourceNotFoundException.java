package com.griddynamics.backoffice.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BackofficeException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException() {
        super(HTTP_STATUS);
    }

    public ResourceNotFoundException(String message) {
        super(message, HTTP_STATUS);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause, HTTP_STATUS);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause, HTTP_STATUS);
    }
}
