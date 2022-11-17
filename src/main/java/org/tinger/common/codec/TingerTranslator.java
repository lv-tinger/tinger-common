package org.tinger.common.codec;

import org.tinger.common.serialize.Serializer;
import org.tinger.common.serialize.impl.Hessian2Serializer;
import org.tinger.common.utils.CodecUtils;
import org.tinger.common.utils.CompressUtils;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 * Created by tinger on 2022-11-13
 */
public class TingerTranslator implements Translator {

    private static final byte[] EMPTY_BYTES = new byte[0];

    private final Serializer serializer = new Hessian2Serializer();

    public static final int DEFAULT_COMPRESSION_THRESHOLD = 16384;

    public static final int SERIALIZED = 1;
    public static final int COMPRESSED = 2;

    public static final int SPECIAL_MASK = 0xff00;
    public static final int SPECIAL_BOOLEAN = (1 << 8);
    public static final int SPECIAL_INT = (2 << 8);
    public static final int SPECIAL_LONG = (3 << 8);
    public static final int SPECIAL_DATE = (4 << 8);
    public static final int SPECIAL_BYTE = (5 << 8);
    public static final int SPECIAL_FLOAT = (6 << 8);
    public static final int SPECIAL_DOUBLE = (7 << 8);
    public static final int SPECIAL_BYTEARRAY = (8 << 8);

    @Override
    public byte[] encode(Object object) {
        if (object == null) {
            return EMPTY_BYTES;
        }

        ByteBuffer buffer = encodeByteBuff(object);
        if (buffer == null) {
            return EMPTY_BYTES;
        }
        return buffer.array();
    }

    @Override
    public ByteBuffer encodeByteBuff(Object object) {
        if (object == null) {
            return null;
        }
        byte[] b;
        int flags = 0;
        if (object instanceof String) {
            b = CodecUtils.getUtf8Bytes((String) object);
        } else if (object instanceof Long) {
            b = CodecUtils.encodeLong((long) object);
            flags |= SPECIAL_LONG;
        } else if (object instanceof Integer) {
            b = CodecUtils.encodeLong((int) object);
            flags |= SPECIAL_INT;
        } else if (object instanceof Boolean) {
            b = CodecUtils.encodeBoolean((boolean) object);
            flags |= SPECIAL_BOOLEAN;
        } else if (object instanceof Date) {
            b = CodecUtils.encodeLong(((Date) object).getTime());
            flags |= SPECIAL_DATE;
        } else if (object instanceof Byte) {
            b = CodecUtils.encodeByte((byte) object);
            flags |= SPECIAL_BYTE;
        } else if (object instanceof Float) {
            b = CodecUtils.encodeInt(Float.floatToRawIntBits((Float) object));
            flags |= SPECIAL_FLOAT;
        } else if (object instanceof Double) {
            b = CodecUtils.encodeLong(Double.doubleToLongBits((Double) object));
            flags |= SPECIAL_DOUBLE;
        } else if (object instanceof byte[]) {
            b = (byte[]) object;
            flags |= SPECIAL_BYTEARRAY;
        } else {
            b = serializer.serialize(object);
            flags |= SERIALIZED;
        }

        if (b.length > DEFAULT_COMPRESSION_THRESHOLD) {
            byte[] compress = CompressUtils.gzipCompress(b);
            if (compress.length < b.length) {
                b = compress;
                flags |= COMPRESSED;
            }
        }

        ByteBuffer buffer = ByteBuffer.allocate(4 + b.length);
        buffer.putInt(flags);
        buffer.put(b);

        return buffer;
    }

    @Override
    public Object decode(byte[] bytes) {
        if (bytes == null || bytes.length <= 4) {
            return null;
        }
        ByteBuffer bb = ByteBuffer.wrap(bytes);

        byte[] fb = new byte[4];
        bb.get(fb, 0, 4);
        int flags = CodecUtils.decodeInt(fb);

        int len = bytes.length - 4;
        bytes = new byte[len];
        bb.get(bytes);

        if ((flags & COMPRESSED) == COMPRESSED) {
            bytes = CompressUtils.gzipDecompress(bytes);
        }

        if (flags == SERIALIZED) {
            return serializer.deserialize(bytes);
        }

        flags = flags & SPECIAL_MASK;
        if (flags == 0) {
            return CodecUtils.newStringUtf8(bytes);
        }

        switch (flags) {
            case SPECIAL_LONG:
                return CodecUtils.decodeLong(bytes);
            case SPECIAL_INT:
                return CodecUtils.decodeInt(bytes);
            case SPECIAL_BOOLEAN:
                return CodecUtils.decodeBoolean(bytes);
            case SPECIAL_DATE:
                return new Date(CodecUtils.decodeLong(bytes));
            case SPECIAL_BYTE:
                return CodecUtils.decodeByte(bytes);
            case SPECIAL_FLOAT:
                return Float.intBitsToFloat(CodecUtils.decodeInt(bytes));
            case SPECIAL_DOUBLE:
                return Double.longBitsToDouble(CodecUtils.decodeLong(bytes));
            case SPECIAL_BYTEARRAY:
                return bytes;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public Object decodeByteBuffer(ByteBuffer byteBuffer) {
        return decode(byteBuffer.array());
    }
}
