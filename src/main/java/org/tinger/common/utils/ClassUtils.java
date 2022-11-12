package org.tinger.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tinger on 2022-10-01
 */
public class ClassUtils {
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ignore) {
        }
        if (classLoader == null) {
            classLoader = ClassUtils.class.getClassLoader();
            if (classLoader == null) {
                try {
                    classLoader = ClassLoader.getSystemClassLoader();
                } catch (Throwable ignore) {
                }
            }
        }
        return classLoader;
    }

    public static <T extends Annotation> T getAnnotation(Class<?> type, Class<T> annoType) {
        if (type == null || annoType == null) {
            return null;
        }

        return type.getDeclaredAnnotation(annoType);
    }

    public static String getSimpleName(Class<?> type) {
        if (type == null) {
            return null;
        }

        return type.getSimpleName();
    }

    public static String getClassName(Class<?> type) {
        if (type == null) {
            return null;
        }

        return type.getName();
    }

    public static List<Class<?>> getGenericSuperclass(Object object) {
        Class<?> type = object.getClass();
        if (Object.class.equals(type)) {
            throw new RuntimeException();
        }
        Type superclass = type.getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return Arrays.stream(parameterizedType.getActualTypeArguments()).map(x -> (Class<?>) x).collect(Collectors.toList());
    }
}
