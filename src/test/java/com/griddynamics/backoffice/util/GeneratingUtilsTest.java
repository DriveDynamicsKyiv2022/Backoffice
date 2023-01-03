package com.griddynamics.backoffice.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratingUtilsTest {

    @Test
    void generateId() {
        String id = GeneratingUtils.generateId();

        assertTrue(id != null && !id.isEmpty());
    }
}