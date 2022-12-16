package com.griddynamics.backoffice.exception;

public class PaginationException extends RuntimeException {
    public PaginationException() {
    }

    public PaginationException(String message) {
        super(message);
    }

    public PaginationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaginationException(Throwable cause) {
        super(cause);
    }

}
