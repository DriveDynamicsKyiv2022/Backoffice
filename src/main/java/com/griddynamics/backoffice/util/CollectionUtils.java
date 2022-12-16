package com.griddynamics.backoffice.util;

import org.springframework.data.domain.Page;

import java.util.Collection;

public class CollectionUtils {
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
