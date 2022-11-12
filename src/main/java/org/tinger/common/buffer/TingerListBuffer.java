package org.tinger.common.buffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by tinger on 2022-09-21
 */
public class TingerListBuffer<T> {
    private final List<T> list;
    private final ReadWriteLock locker;

    public TingerListBuffer() {
        this(new ArrayList<>(), new ReentrantReadWriteLock());
    }

    public TingerListBuffer(List<T> list) {
        this(list, new ReentrantReadWriteLock());
    }

    public TingerListBuffer(ReadWriteLock locker) {
        this(new ArrayList<>(), locker);
    }

    public TingerListBuffer(List<T> list, ReadWriteLock locker) {
        this.list = list;
        this.locker = locker;
    }

    public boolean add(T object) {
        if (list.contains(object)) {
            return false;
        }
        locker.writeLock().lock();
        try {
            if (list.contains(object)) {
                return false;
            }
            return list.add(object);
        } finally {
            locker.writeLock().unlock();
        }
    }

    public boolean del(T object) {
        locker.writeLock().lock();
        try {
            if (!list.contains(object)) {
                return false;
            }
            return list.remove(object);
        } finally {
            locker.writeLock().unlock();
        }
    }

    public T get(int index) {
        locker.readLock().lock();
        try {
            if (list.size() < index) {
                return null;
            }

            return list.get(index);
        } finally {
            locker.readLock().unlock();
        }

    }

    public T get(Predicate<T> predicate) {
        locker.readLock().lock();
        try {
            List<T> collect = list.stream().filter(predicate).collect(Collectors.toList());
            if (collect.size() == 0) {
                return null;
            } else if (collect.size() > 1) {
                throw new RuntimeException();
            }

            return collect.get(0);
        } finally {
            locker.readLock().unlock();
        }
    }

    public T get(Predicate<T> predicate, Supplier<T> supplier) {
        T object = get(predicate);
        if (object != null) {
            return object;
        }

        locker.readLock().lock();
        try {
            object = get(predicate);
            if (object != null) {
                return object;
            }
            object = supplier.get();
            list.add(object);
            return object;
        } finally {
            locker.readLock().unlock();
        }
    }

    public List<T> dump() {
        locker.readLock().lock();
        try {
            return new ArrayList<>(list);
        } finally {
            locker.readLock().unlock();
        }
    }

    public List<T> dumpUnSafe() {
        return Collections.unmodifiableList(this.list);
    }

    public void clear() {
        locker.writeLock().lock();
        try {
            list.clear();
        } finally {
            locker.writeLock().unlock();
        }
    }
}
