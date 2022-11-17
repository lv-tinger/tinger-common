package org.tinger.common.codec;

import java.nio.ByteBuffer;

/**
 * Created by tinger on 2022-11-13
 */
public interface Translator {
    byte[] encode(Object object);

    ByteBuffer encodeByteBuff(Object object);

    Object decode(byte[] bytes);

    Object decodeByteBuffer(ByteBuffer byteBuffer);
}
