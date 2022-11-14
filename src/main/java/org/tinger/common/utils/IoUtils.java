package org.tinger.common.utils;

import java.io.Closeable;

public class IoUtils {
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignore) {

            }
        }
    }

    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignore) {

            }
        }
    }
}
