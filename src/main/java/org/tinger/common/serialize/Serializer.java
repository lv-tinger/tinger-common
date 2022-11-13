package org.tinger.common.serialize;

import org.tinger.common.utils.ServiceLoaderUtils;

public interface Serializer {
    byte[] serialize(Object object);

    Object deserialize(byte[] bytes);

    static Serializer getInstance() {
        return HOLDER.serializer;
    }

    class HOLDER {
        private static final Serializer serializer;

        static {
            serializer = ServiceLoaderUtils.load(Serializer.class);
        }
    }
}