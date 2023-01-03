package com.griddynamics.backoffice.util;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollectionUtilsTest {

    @Test
    void testIsEmpty() {
        Collection<?> collection = Collections.EMPTY_LIST;

        assertTrue(CollectionUtils.isEmpty(collection));
    }

    @Test
    void testIsNull() {
        assertTrue(CollectionUtils.isEmpty(null));
    }

    @Test
    void testIsNotEmpty() {
        Collection<?> collection = List.of(1, 3);

        assertFalse(CollectionUtils.isEmpty(collection));
    }
}