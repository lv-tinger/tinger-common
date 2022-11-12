package org.tinger.common.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by tinger on 2022-10-01
 */
public class ArrayUtils {
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static int getLength(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    public static boolean isEmpty(final int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(final Object array) {
        if (array == null) {
            return true;
        }

        return Array.getLength(array) == 0;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] repeat(T object, int length) {
        if (object == null) {
            return null;
        }
        T[] instance = (T[]) Array.newInstance(object.getClass(), length);
        for (int i = 0; i < length; i++) {
            instance[i] = object;
        }

        return instance;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> collection, Class<?> type) {
        if (collection == null) {
            return null;
        }
        int length = collection.size();
        T[] instance = (T[]) Array.newInstance(type, length);
        return collection.toArray(instance);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] attach(T[] original, T... addition) {
        if (original == null && addition == null) {
            return null;
        }
        if (isEmpty(original)) {
            return addition;
        }

        if (isEmpty(addition)) {
            return original;
        }

        T[] result = (T[]) Array.newInstance(original.getClass().getComponentType(), original.length + addition.length);

        System.arraycopy(original, 0, result, 0, original.length);
        System.arraycopy(addition, 0, result, original.length, original.length);

        return result;
    }

    public static <T> boolean equals(T[] array1, T[] array2) {
        if (array1 == null && array2 == null) {
            return true;
        }

        if (array1 == null || array2 == null) {
            return false;
        }

        if (array1.length != array2.length) {
            return false;
        }

        int length = array1.length;

        if (length == 0) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Objects.equals(array1[i], array2[i])) {
                return false;
            }
        }

        return true;
    }
}
