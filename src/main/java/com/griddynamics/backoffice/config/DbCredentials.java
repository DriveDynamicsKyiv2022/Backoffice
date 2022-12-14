package com.griddynamics.backoffice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class DbCredentials {
    private final String uri;
    private final String dbName;
}
