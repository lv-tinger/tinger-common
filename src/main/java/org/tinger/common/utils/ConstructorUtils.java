package org.tinger.common.utils;

import java.lang.reflect.Constructor;

/**
 * Created by tinger on 2022-11-13
 */
public class ConstructorUtils {
    public static <T> Constructor<T> get(Class<T> type) {
        if (type == null) {
            return null;
        }

        try {
            return type.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Constructor<T> getDeclaredConstructor(Class<T> type, Class<?>... parameterTypes) {
        if (type == null) {
            return null;
        }

        try {
            return type.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
