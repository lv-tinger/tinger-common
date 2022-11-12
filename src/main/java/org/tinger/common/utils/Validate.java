package org.tinger.common.utils;

/**
 * Created by tinger on 2022-10-01
 */
public class Validate {
    private static final String DEFAULT_VALIDATE_MESSAGE = "validate expression is false";

    public static void isTrue(final boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException(DEFAULT_VALIDATE_MESSAGE);
        }
    }

    public static void isTrue(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(final boolean expression, final String message, final int value) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, value));
        }
    }

    public static void isTrue(final boolean expression, final String message, final double value) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, value));
        }
    }

    public static void isTrue(final boolean expression, final String message, final float value) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, value));
        }
    }

    public static void isTrue(final boolean expression, final String message, final long value) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, value));
        }
    }
}
