package org.tinger.common.utils;

import java.util.Random;

/**
 * Created by tinger on 2022-10-01
 */
public class RandomUtils {
    private static final Random RANDOM = new Random();

    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    public static byte[] nextBytes(final int count) {
        final byte[] result = new byte[count];
        RANDOM.nextBytes(result);
        return result;
    }

    public static int nextInteger() {
        return nextInteger(0, Integer.MAX_VALUE);
    }

    public static int nextInteger(final int start, final int end) {
        Validate.isTrue(end >= start);
        Validate.isTrue(start >= 0);

        return start == end ? start : start + RANDOM.nextInt(end - start);
    }

    public static int nextInteger(final int start) {
        return nextInteger(start, Integer.MAX_VALUE);
    }

    public static long nextLong() {
        return nextLong(Long.MAX_VALUE);
    }

    public static long nextLong(final long n) {
        long bits;
        long val;
        do {
            bits = RANDOM.nextLong() >>> 1;
            val = bits % n;
        } while (bits - val + (n - 1) < 0);

        return val;
    }

    public static long nextLong(final long start, final long end) {
        Validate.isTrue(end >= start);
        Validate.isTrue(start >= 0);
        return start == end ? start : start + nextLong(end - start);
    }
}
