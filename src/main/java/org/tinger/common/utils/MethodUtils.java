package org.tinger.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tinger on 2022-11-13
 */
public class MethodUtils {
    private static final Method[] EMPTY_METHOD = new Method[0];

    public static <T extends Annotation> T getAnnotation(Method method, Class<T> annoType) {
        if (method == null || annoType == null) {
            return null;
        }

        return method.getDeclaredAnnotation(annoType);
    }

    public static Method[] getDeclaredMethod(Class<?> type) {
        Method[] methods = null;
        Class<?> current = type;
        while (!current.equals(Object.class)) {
            methods = ArrayUtils.attach(methods, current.getDeclaredMethods());
            current = current.getSuperclass();
        }
        return methods;
    }

    public static Method[] getDeclaredMethod(Class<?> type, String name) {
        if (type == null || StringUtils.isEmpty(name)) {
            return EMPTY_METHOD;
        }
        Method[] methods = getDeclaredMethod(type);
        List<Method> collect = Arrays.stream(methods).filter(x -> x.getName().equals(name)).collect(Collectors.toList());
        return ArrayUtils.toArray(collect, Method.class);
    }

    public static Method getDeclaredMethod(Class<?> type, String name, Class<?>... parameters) {
        if (type == null || StringUtils.isEmpty(name)) {
            return null;
        }

        Method[] methods = getDeclaredMethod(type);
        for (Method method : methods) {
            if (ArrayUtils.equals(method.getParameterTypes(), parameters)) {
                return method;
            }
        }

        throw new RuntimeException();
    }

}
