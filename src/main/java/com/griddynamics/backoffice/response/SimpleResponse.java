package com.griddynamics.backoffice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class SimpleResponse {
    private final String message;
}
