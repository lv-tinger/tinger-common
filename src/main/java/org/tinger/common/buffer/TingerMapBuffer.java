package org.tinger.common.buffer;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TingerMapBuffer<K, V> {
    private final Map<K, V> mapper;
    private final Lock locker = new ReentrantLock();

    public TingerMapBuffer() {
        this(new HashMap<>());
    }

    public TingerMapBuffer(Map<K, V> mapper) {
        this.mapper = mapper;
    }

    public V get(K key) {
        Objects.requireNonNull(key);
        return this.mapper.get(key);
    }

    public V get(K key, Supplier<V> supplier) {
        V value = mapper.get(key);
        if (value == null) {
            if (mapper.containsKey(key)) {
                return null;
            }
            locker.lock();
            value = this.mapper.get(key);
            if (value == null) {
                if (mapper.containsKey(key)) {
                    return null;
                }
                try {
                    value = supplier.get();
                    mapper.put(key, value);
                } finally {
                    locker.unlock();
                }
            }
        }
        return value;
    }

    public V get(K key, Predicate<V> predicate, Supplier<V> supplier) {
        V value = mapper.get(key);
        if (value == null || !predicate.test(value)) {
            locker.lock();
            value = mapper.get(key);
            if (value == null || !predicate.test(value)) {
                try {
                    value = supplier.get();
                    mapper.put(key, value);
                } finally {
                    locker.unlock();
                }
            }
        }
        return value;
    }

    public void set(K key, V value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        locker.lock();
        try {
            mapper.put(key, value);
        } finally {
            locker.unlock();
        }
    }

    public V putIfAbsent(K key, V value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        locker.lock();
        try {
            return mapper.putIfAbsent(key, value);
        } finally {
            locker.unlock();
        }
    }

    public V del(K key) {
        locker.lock();
        try {
            return this.mapper.remove(key);
        } finally {
            locker.unlock();
        }
    }

    public Set<K> keys(){
        return Collections.unmodifiableSet(this.mapper.keySet());
    }

    public Collection<V> values(){
        return Collections.unmodifiableCollection(this.mapper.values());
    }

    public void clear() {
        locker.lock();
        try {
            this.mapper.clear();
        } finally {
            locker.lock();
        }
    }
}