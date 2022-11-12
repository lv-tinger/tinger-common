package org.tinger.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by tinger on 2022-10-20
 */
public class FieldUtils {
    private static final Field[] EMPTY_FIELD = new Field[0];

    public static <T extends Annotation> T getAnnotation(Field field, Class<T> type) {
        if (field == null) {
            return null;
        }

        return field.getDeclaredAnnotation(type);
    }

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

    public static Field[] getDeclaredFields(Class<?> type) {
        Field[] fields = null;
        Class<?> current = type;
        while (!current.equals(Object.class)) {
            fields = ArrayUtils.attach(current.getDeclaredFields(), fields);
            current = current.getSuperclass();
        }
        return fields == null ? EMPTY_FIELD : fields;
    }

    public static Field[] getMemberField(Class<?> type) {
        Predicate<Field> predicate = field -> {
            if (field == null) {
                return false;
            }
            int modifiers = field.getModifiers();
            return !(Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || Modifier.isTransient(modifiers) || Modifier.isStrict(modifiers) || Modifier.isVolatile(modifiers));
        };

        return getDeclaredFields(type, predicate);
    }

    public static Field getDeclaredField(Class<?> type, String name) {
        if (type == null || StringUtils.isEmpty(name)) {
            return null;
        }

        Field[] fields = getDeclaredFields(type);
        if (fields.length == 0) {
            return null;
        }

        for (Field field : fields) {
            if (field.getName().equals(name)) {
                return field;
            }
        }

        return null;
    }

    public static Field[] getDeclaredFields(Class<?> type, Predicate<Field> predicate) {
        if (type == null) {
            return EMPTY_FIELD;
        }

        Field[] fields = getDeclaredFields(type);
        if (fields.length == 0) {
            return EMPTY_FIELD;
        }

        List<Field> collect = Arrays.stream(fields).filter(predicate).collect(Collectors.toList());
        return collect.toArray(new Field[0]);
    }
}
