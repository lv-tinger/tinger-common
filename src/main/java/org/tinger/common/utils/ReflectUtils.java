package org.tinger.common.utils;

import org.tinger.common.buffer.TingerMapBuffer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by tinger on 2022-10-18
 */
public class ReflectUtils {

    private static final Field[] EMPTY_FIELD = new Field[0];
    private static final Method[] EMPTY_METHOD = new Method[0];

    private static final TingerMapBuffer<Class<?>, Field[]> FIELD_BUFFER = new TingerMapBuffer<>(new WeakHashMap<>());
    private static final TingerMapBuffer<Class<?>, Method[]> METHOD_BUFFER = new TingerMapBuffer<>(new WeakHashMap<>());
    private static final TingerMapBuffer<Class<?>, Constructor<?>[]> CONSTRUCTOR_BUFFER = new TingerMapBuffer<>(new WeakHashMap<>());

    public static Field[] getFields(Class<?> type) {
        if (type == null) {
            return EMPTY_FIELD;
        }

        return FIELD_BUFFER.get(type, () -> {
            Field[] fields = null;
            Class<?> current = type;
            while (!current.equals(Object.class)) {
                fields = ArrayUtils.attach(current.getDeclaredFields(), fields);
                current = current.getSuperclass();
            }
            return fields == null ? EMPTY_FIELD : fields;
        });
    }

    public static Field getField(Class<?> type, String name) {
        if (type == null || StringUtils.isEmpty(name)) {
            return null;
        }

        Field[] fields = getFields(type);
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

    public static Field[] getFields(Class<?> type, Predicate<Field> predicate) {
        if (type == null) {
            return EMPTY_FIELD;
        }

        Field[] fields = getFields(type);
        if (fields.length == 0) {
            return EMPTY_FIELD;
        }

        List<Field> collect = Arrays.stream(fields).filter(predicate).collect(Collectors.toList());
        return collect.toArray(new Field[0]);
    }

    public static Field[] getMemberFields(Class<?> type) {
        Predicate<Field> p = field -> {
            if (field == null) {
                return false;
            }

            int modifiers = field.getModifiers();
            return !(Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || Modifier.isTransient(modifiers) || Modifier.isStrict(modifiers) || Modifier.isVolatile(modifiers));
        };

        return getFields(type, p);
    }

    public static Method[] getMethod(Class<?> type) {
        if (type == null) {
            return EMPTY_METHOD;
        }

        return METHOD_BUFFER.get(type, () -> {
            Method[] methods = null;
            Class<?> current = type;
            while (!current.equals(Object.class)) {
                methods = ArrayUtils.attach(methods, current.getDeclaredMethods());
                current = current.getSuperclass();
            }
            return methods;
        });
    }

    public static Method getMethod(Class<?> type, String name) {
        Method[] methods = getMethod(type);
        for (Method method : methods) {
            if (StringUtils.equals(method.getName(), name)) {
                return method;
            }
        }

        return null;
    }

    public static Method getMethod(Class<?> type, String name, Class<?>... parameters) {
        if (type == null || StringUtils.isEmpty(name)) {
            return null;
        }
        Method[] methods = getMethod(type);
        for (Method method : methods) {
            if (ArrayUtils.equals(method.getParameterTypes(), parameters)) {
                return method;
            }
        }

        return null;
    }

    public static Constructor<?>[] getConstructors(Class<?> type) {
        return CONSTRUCTOR_BUFFER.get(type, type::getConstructors);
    }

    public static <T> Constructor<T> getConstructor(Class<T> type) {
        Constructor<?>[] constructors = getConstructors(type);
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == 0) {
                return (Constructor<T>) constructor;
            }
        }

        return null;
    }

    public static Constructor<?> getConstructor(Class<?> type, Class<?>... parameters) {
        Constructor<?>[] constructors = getConstructors(type);
        for (Constructor<?> constructor : constructors) {
            if (ArrayUtils.equals(constructor.getParameterTypes(), parameters)) {
                return constructor;
            }
        }

        return null;
    }

    public static void clean() {
        FIELD_BUFFER.clear();
        METHOD_BUFFER.clear();
        CONSTRUCTOR_BUFFER.clear();
    }
}
