package org.tinger.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by tinger on 2022-10-20
 */
public class FieldUtils {
    public static String getName(Field field) {
        if (field == null) {
            return null;
        }

        return field.getName();
    }

    public static Class<?> getType(Field field) {
        if (field == null) {
            return null;
        }

        return field.getType();
    }

    public static <T extends Annotation> T getAnnotation(Field field, Class<T> type) {
        if (field == null) {
            return null;
        }

        return field.getDeclaredAnnotation(type);
    }
}
