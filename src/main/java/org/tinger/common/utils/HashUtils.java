package org.tinger.common.utils;

import org.tinger.common.codec.MurmurHash3;

/**
 * Created by tinger on 2022-11-07
 */
public class HashUtils {
    public static int murmurHash(String str) {
        if (str == null) {
            return 0;
        }
        return MurmurHash3.murmurhash3_x86_32(CodecUtils.getUtf8Bytes(str));
    }
}
