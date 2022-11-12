package org.tinger.common.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

public class ServiceLoaderUtils {
    public static <T> T load(Class<T> serviceType) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(serviceType, ClassUtils.getDefaultClassLoader());
        Iterator<T> iterator = serviceLoader.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

    public static <T> List<T> scan(Class<T> serviceType) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(serviceType, ClassUtils.getDefaultClassLoader());
        List<T> list = new LinkedList<>();

        for (T service : serviceLoader) {
            list.add(service);
        }

        return list;
    }
}