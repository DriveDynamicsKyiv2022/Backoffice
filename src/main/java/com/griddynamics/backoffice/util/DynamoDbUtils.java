package com.griddynamics.backoffice.util;

import com.griddynamics.backoffice.model.DynamoLogicalOperator;

public class DynamoDbUtils {
    public static String appendFilterExpression(String filterExpression, String expressionToAppend, DynamoLogicalOperator logicalOperator) {
        if (VariablesUtils.stringIsNullOrEmpty(filterExpression) && VariablesUtils.stringIsNullOrEmpty(expressionToAppend)) {
            throw new IllegalArgumentException("At least one part must be specified");
        }
        if (VariablesUtils.stringIsNullOrEmpty(filterExpression)) {
            return expressionToAppend;
        }
        if (VariablesUtils.stringIsNullOrEmpty(expressionToAppend)) {
            return filterExpression;
        }
        if (logicalOperator == null) {
            throw new IllegalArgumentException("Logical operator must be valid");
        }
        return String.format("%s %s %s", filterExpression, logicalOperator.name(), expressionToAppend);
    }
}
