package com.griddynamics.backoffice.exception;

import org.springframework.http.HttpStatus;

public class DatabaseException extends BackofficeException {
    public static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public DatabaseException() {
        super(HTTP_STATUS);
    }

    public DatabaseException(String message) {
        super(message, HTTP_STATUS);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause, HTTP_STATUS);
    }

    public DatabaseException(Throwable cause) {
        super(cause, HTTP_STATUS);
    }
}
