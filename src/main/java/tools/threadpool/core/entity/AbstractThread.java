package tools.threadpool.core.entity;

import java.util.concurrent.BlockingQueue;

public class AbstractThread extends Thread{

    protected final BlockingQueue<Runnable> taskQueue;

    public AbstractThread(final BlockingQueue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
    }


}
