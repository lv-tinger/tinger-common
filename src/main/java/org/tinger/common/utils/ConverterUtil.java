package org.tinger.common.utils;

import java.time.Instant;
import java.util.Date;

/**
 * Created by tinger on 2022-10-08
 */
public class ConverterUtil {

    public static byte toByte(Object value) {
        byte defaultValue = 0;
        return toByte(value, defaultValue);
    }

    public static byte toByte(Object value, byte defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        if (value instanceof Byte) {
            return (byte) value;
        }

        if (value instanceof Number) {
            return ((Number) value).byteValue();
        }

        try {
            String string = value.toString();
            return Byte.parseByte(trim(string));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int toInteger(Object value) {
        return toInteger(value, 0);
    }

    public static int toInteger(Object value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        try {
            String string = value.toString();
            return Integer.parseInt(trim(string));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long toLong(Object value) {
        return toLong(value, 0L);
    }

    public static long toLong(Object value, long defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        if (value instanceof Number) {
            return ((Number) value).longValue();
        }

        if (value instanceof Date) {
            return ((Date) value).getTime();
        }

        if (value instanceof Instant) {
            return ((Instant) value).toEpochMilli();
        }

        try {
            String string = value.toString();
            return Long.parseLong(trim(string));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static double toDouble(Object value) {
        return toDouble(value, 0D);
    }

    public static double toDouble(Object value, double defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }

        try {
            String string = value.toString();
            return Double.parseDouble(trim(string));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static float toFloat(Object value) {
        return toFloat(value, 0F);
    }

    public static float toFloat(Object value, float defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }

        try {
            String string = value.toString();
            return Float.parseFloat(trim(string));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean toBoolean(Object value) {
        return toBoolean(value, false);
    }

    public static boolean toBoolean(Object value, boolean defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        if (value instanceof Boolean) {
            return (boolean) value;
        }

        String string = value.toString();
        return Boolean.parseBoolean(trim(string));
    }

    public static String toString(Object value) {
        return toString(value, null);
    }

    public static String toString(Object value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        if (value instanceof String) {
            return (String) value;
        }

        if (value.getClass().isEnum()) {
            return ((Enum<?>) value).name();
        }


        return value.toString();
    }

    private static String trim(String string) {
        return string == null ? "" : string.trim();
    }
}
