package org.tinger.common.thread;

import org.tinger.common.utils.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class NamedThreadFactory implements ThreadFactory {
    private final String prefix;
    private final boolean daemon;
    private final ThreadGroup threadGroup;
    private final AtomicLong counter = new AtomicLong(1);

    private final Thread.UncaughtExceptionHandler handler;

    private NamedThreadFactory(String name, boolean daemon, Thread.UncaughtExceptionHandler handler) {
        this.prefix = name + "-thread-";
        this.daemon = daemon;
        this.handler = handler;
        SecurityManager securityManager = System.getSecurityManager();
        threadGroup = securityManager == null ? Thread.currentThread().getThreadGroup() : securityManager.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String threadName = this.prefix + counter.getAndIncrement();
        Thread thread = new Thread(threadGroup, runnable, threadName);
        if (!thread.isDaemon()) {
            if (this.daemon) {
                thread.setDaemon(true);
            }
        } else if (!this.daemon) {
            thread.setDaemon(false);
        }
        thread.setPriority(Thread.NORM_PRIORITY);
        if (this.handler != null) {
            thread.setUncaughtExceptionHandler(handler);
        }
        return thread;
    }

    private static final ConcurrentHashMap<String, ThreadFactory> holder = new ConcurrentHashMap<>();

    public static ThreadFactory getInstance(String name) {
        return getInstance(name, false, null);
    }

    public static ThreadFactory getInstance(String name, boolean daemon, Thread.UncaughtExceptionHandler handler) {
        String threadName = StringUtils.isEmpty(name) ? "tinger" : name;
        return holder.computeIfAbsent(threadName, s -> new NamedThreadFactory(threadName, daemon, handler));
    }
}
