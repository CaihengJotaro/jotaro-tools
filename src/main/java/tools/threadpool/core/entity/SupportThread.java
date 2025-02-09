package tools.threadpool.core.entity;

import java.sql.Time;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class SupportThread extends AbstractThread{

    private int timeout;
    private TimeUnit timeUnit;

    public SupportThread(final int timeout,final TimeUnit timeUnit,
        final BlockingQueue<Runnable> blockingQueue) {
        super(blockingQueue);
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    @Override
    public void run() {
        while (true){
            try {
                Runnable task = taskQueue.poll(timeout,timeUnit);
                if(task == null){
                    return;
                }
                task.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
