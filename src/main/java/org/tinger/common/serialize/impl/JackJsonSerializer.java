package org.tinger.common.serialize.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.tinger.common.serialize.JsonSerializer;
import org.tinger.common.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Map;
import java.util.TimeZone;

public class JackJsonSerializer implements JsonSerializer {

    private static final ObjectMapper OM = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule())
            .setTimeZone(TimeZone.getDefault())
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .registerModules(new ParameterNamesModule(), new Jdk8Module());

    private static final JavaType MAP_JAVA_TYPE = TypeFactory.defaultInstance().constructParametricType(Map.class, String.class, Object.class);

    @Override
    public String toJson(Object bean) {
        if (bean == null) {
            return null;
        }

        try {
            return OM.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> fromJson(String json) {
        if (StringUtils.isEmpty(json)) {
            return Collections.emptyMap();
        }

        try {
            return OM.readValue(json, MAP_JAVA_TYPE);
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    @Override
    public <T> T fromJson(String json, Class<T> type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return OM.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T fromJson(String json, Class<T> type, Class<?>... genericTypes) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            JavaType javaType = TypeFactory.defaultInstance().constructParametricType(type, genericTypes);
            return OM.readValue(json, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
