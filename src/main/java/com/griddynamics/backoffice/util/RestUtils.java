package com.griddynamics.backoffice.util;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class RestUtils {
    public static URI buildUri(UriComponentsBuilder uriComponentsBuilder, String... pathSegments) {
        return uriComponentsBuilder.
                pathSegment(pathSegments)
                .build()
                .toUri();
    }
}
