package tools.threadpool.core.entity;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import javax.security.auth.callback.Callback;

public class CoreThread extends AbstractThread{

    public CoreThread(final BlockingQueue<Runnable> blockingQueue) {
        super(blockingQueue);
    }

    @Override
    public void run() {
        while(true){
            try {
                Runnable task = taskQueue.take();
                task.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
