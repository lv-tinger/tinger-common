package org.tinger.common.codec;

/**
 * Created by tinger on 2022-11-13
 */
public interface Translator {
    byte[] encode(Object object);

    Object decode(byte[] bytes);
}
