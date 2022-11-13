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

    public static String newString(byte[] bytes, String charset) {
        if (bytes == null) {
            return null;
        }
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encodeNumber(long number, int maxBytes, boolean skipZero) {
        byte[] rv = new byte[maxBytes];
        for (int i = 0; i < rv.length; i++) {
            int pos = rv.length - i - 1;
            rv[pos] = (byte) ((number >> (8 * i)) & 0xff);
        }
        if (skipZero) {
            int firstNon0 = 0;
            while (firstNon0 < rv.length && rv[firstNon0] == 0) {
                firstNon0++;
            }
            if (firstNon0 > 0) {
                byte[] tmp = new byte[rv.length - firstNon0];
                System.arraycopy(rv, firstNon0, tmp, 0, rv.length - firstNon0);
                rv = tmp;
            }
        }
        return rv;
    }

    public static byte[] encodeLong(long l) {
        return encodeNumber(l, 8, true);
    }

    public static long decodeLong(byte[] b) {
        long rv = 0;
        for (byte i : b) {
            rv = (rv << 8) | (i < 0 ? 256 + i : i);
        }
        return rv;
    }


    public static byte[] encodeInt(int in) {
        return encodeNumber(in, 4, true);
    }

    public static int decodeInt(byte[] in) {
        return (int) decodeLong(in);
    }

    public static byte[] encodeByte(byte in) {
        return new byte[]{in};
    }

    public static byte decodeByte(byte[] in) {
        byte rv = 0;
        if (in.length == 1) {
            rv = in[0];
        }
        return rv;
    }

    public static byte[] encodeBoolean(boolean b) {
        byte[] rv = new byte[1];
        rv[0] = (byte) (b ? '1' : '0');
        return rv;
    }

    public static boolean decodeBoolean(byte[] in) {
        return in[0] == '1';
    }

}
