package com.griddynamics.backoffice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(staticName = "of")
@Builder
@Getter
public class ServerError {
    private HttpStatus httpStatus;
    private int code;
    private long timestamp;
    private String message;
}
