package org.tinger.common.serialize;

import org.tinger.common.utils.ServiceLoaderUtils;

import java.util.Map;

public interface JsonSerializer {
    String toJson(Object bean);

    Map<String, Object> fromJson(String json);

    <T> T fromJson(String json, Class<T> type);

    <T> T fromJson(String json, Class<T> type, Class<?>... genericTypes);

    static JsonSerializer getInstance() {
        return HOLDER.jsonSerialize;
    }

    class HOLDER {
        private static final JsonSerializer jsonSerialize;

        static {
            jsonSerialize = ServiceLoaderUtils.load(JsonSerializer.class);
        }
    }
}
