package com.griddynamics.backoffice.util;

import com.griddynamics.backoffice.model.DynamoLogicalOperator;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DynamoDbUtilsTest {

    @Test
    void appendFilterExpression() {
        String expectedExpression = "count < :number AND name = :name";

        String filterExpression = DynamoDbUtils.appendFilterExpression("count < :number",
                "name = :name", DynamoLogicalOperator.AND);

        assertEquals(expectedExpression, filterExpression);
    }

    @Test
    void appendNull() {
        String expectedExpression = "count < :number";

        String filterExpression = DynamoDbUtils.appendFilterExpression(expectedExpression, null,
                DynamoLogicalOperator.AND);

        assertEquals(expectedExpression, filterExpression);
    }

    @Test
    void appendEmpty() {
        String expectedExpression = "count < :number";

        String filterExpression = DynamoDbUtils.appendFilterExpression(expectedExpression, Strings.EMPTY,
                DynamoLogicalOperator.AND);

        assertEquals(expectedExpression, filterExpression);
    }

    @Test
    void appendToNull() {
        String expectedExpression = "count < :number";

        String filterExpression = DynamoDbUtils.appendFilterExpression(null, expectedExpression,
                DynamoLogicalOperator.AND);

        assertEquals(expectedExpression, filterExpression);
    }

    @Test
    void appendToEmpty() {
        String expectedExpression = "count < :number";

        String filterExpression = DynamoDbUtils.appendFilterExpression(Strings.EMPTY, expectedExpression,
                DynamoLogicalOperator.AND);

        assertEquals(expectedExpression, filterExpression);
    }

    @Test
    void appendWithNoOperator() {
        assertThrows(IllegalArgumentException.class, () -> DynamoDbUtils.appendFilterExpression("count < :number",
                "name = :name", null));
    }

    @Test
    void appendNullToNull() {
        assertThrows(IllegalArgumentException.class, () -> DynamoDbUtils.appendFilterExpression(null,
                null, DynamoLogicalOperator.AND));
    }

    @Test
    void appendNullToEmpty() {
        assertThrows(IllegalArgumentException.class, () -> DynamoDbUtils.appendFilterExpression(Strings.EMPTY,
                null, DynamoLogicalOperator.OR));
    }
}