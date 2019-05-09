package com.gateway.common.support;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author huaili
 * @Date 2019/4/23 18:07
 * @Description JaxwayThreadFactory
 **/
public class JaxwayThreadFactory implements ThreadFactory {

    private final AtomicLong threadNumber = new AtomicLong(1);

    private final String namePrefix;

    private final boolean daemon;

    private static final ThreadGroup threadGroup = new ThreadGroup("JaxWay");

    private JaxwayThreadFactory(String namePrefix,Boolean daemon){
        this.namePrefix = namePrefix;
        this.daemon = daemon;
    }

    public static JaxwayThreadFactory create(String namePrefix,Boolean daemon){
      return  new JaxwayThreadFactory(namePrefix,daemon);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(threadGroup, r,threadGroup.getName() + "-" + namePrefix + "-" + threadNumber.getAndIncrement());
        thread.setDaemon(daemon);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
