package org.tinger.common.serialize;

import org.tinger.common.utils.ServiceLoaderUtils;

public interface Serializer {
    <T> byte[] serialize(T object);

    <T> T deserialize(byte[] bytes, Class<T> type);

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