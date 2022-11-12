package org.tinger.common.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by tinger on 2022-11-07
 */
public class CodecUtils {
    public static byte[] getUtf8Bytes(String str) {
        return getBytes(str, StandardCharsets.UTF_8);
    }

    public static byte[] getBytes(String str, Charset charset) {
        if (str == null) {
            return null;
        }
        return str.getBytes(charset);
    }

    public static ByteBuffer getUtf8Buffer(String str) {
        return getByteBuffer(str, StandardCharsets.UTF_8);
    }

    public static ByteBuffer getByteBuffer(String str, Charset charset) {
        if (str == null) {
            return null;
        }

        return ByteBuffer.wrap(str.getBytes(charset));
    }

    public static String newStringUtf8(byte[] bytes) {
        return newString(bytes, StandardCharsets.UTF_8);
    }

    public static String newString(byte[] bytes, Charset charset) {
        return bytes == null ? null : new String(bytes, charset);
    }

    public static String newString(byte[] bytes, String charset){
        if(bytes == null){
            return null;
        }
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
