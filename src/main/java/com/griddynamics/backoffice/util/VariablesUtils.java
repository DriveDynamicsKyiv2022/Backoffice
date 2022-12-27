package com.griddynamics.backoffice.util;

public class VariablesUtils {
    public static boolean isNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }
}
