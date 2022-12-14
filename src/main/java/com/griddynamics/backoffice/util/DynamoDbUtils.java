package com.griddynamics.backoffice.util;

public class DynamoDbUtils {
    public static String appendFilterExpression(String filterExpression, String expressionToAppend, String logicalOperator) {

        if (filterExpression == null || filterExpression.isEmpty()) {
            return expressionToAppend;
        }
        return String.format("%s %s %s", filterExpression, logicalOperator, expressionToAppend);
    }
}
