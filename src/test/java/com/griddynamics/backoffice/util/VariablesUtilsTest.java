package com.griddynamics.backoffice.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariablesUtilsTest {

    @Test
    void notAllSpecified() {
        assertTrue(VariablesUtils.notAllSpecified(4, null));
    }

    @Test
    void nothingSpecified() {
        assertTrue(VariablesUtils.notAllSpecified());
    }

    @Test
    void allSpecified() {
        assertFalse(VariablesUtils.notAllSpecified(3,"fwdds"));
    }

    @Test
    void nullOrEmpty() {
        assertTrue(VariablesUtils.stringIsNullOrEmpty(null));
    }
}