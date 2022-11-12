package org.tinger.common.utils;

import java.io.Closeable;

/**
 * io工具类
 * Created by tinger on 2022-10-01
 */
public class IoUtils {
    /**
     * 关闭
     *
     * @param closeable nullable
     */
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
