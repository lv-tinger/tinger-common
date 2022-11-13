package org.tinger.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * Created by tinger on 2022-11-13
 */
public class CompressUtils {
    private static final int COMPRESS_RATIO = 8;

    public static byte[] gzipCompress(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ByteArrayOutputStream bos = null;
        GZIPOutputStream gz = null;
        try {
            bos = new ByteArrayOutputStream();
            gz = new GZIPOutputStream(bos);
            gz.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IoUtils.close(gz);
            IoUtils.close(bos);
        }
        return bos.toByteArray();
    }

    public static byte[] gzipDecompress(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        ByteArrayOutputStream bos = null;
        ByteArrayInputStream bis = null;
        GZIPInputStream gis = null;
        try {
            bos = new ByteArrayOutputStream();
            bis = new ByteArrayInputStream(bytes);
            gis = new GZIPInputStream(bis);
            byte[] buf = new byte[64 * 1024];
            int r;
            while ((r = gis.read(buf)) > 0) {
                bos.write(buf, 0, r);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IoUtils.close(gis);
            IoUtils.close(bis);
            IoUtils.close(bos);
        }

        return bos.toByteArray();
    }

    public static byte[] zipCompress(byte[] bytes) {
        ByteArrayOutputStream baos = null;
        DeflaterOutputStream os = null;
        try {
            baos = new ByteArrayOutputStream(bytes.length);
            os = new DeflaterOutputStream(baos);
            os.write(bytes);
            os.finish();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("IO exception compressing data", e);
        } finally {
            IoUtils.close(os);
            IoUtils.close(baos);
        }
    }

    public static byte[] zipDecompress(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int size = bytes.length * COMPRESS_RATIO;
        ByteArrayInputStream bais = null;
        InflaterInputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            is = new InflaterInputStream(bais);
            baos = new ByteArrayOutputStream(size);
            byte[] uncompressMessage = new byte[size];
            while (true) {
                int len = is.read(uncompressMessage);
                if (len <= 0) {
                    break;
                }
                baos.write(uncompressMessage, 0, len);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IoUtils.close(baos);
            IoUtils.close(is);
            IoUtils.close(bais);
        }
    }
}
