package tools.threadpool.core;


import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.springframework.stereotype.Component;
import tools.threadpool.core.entity.AbstractThread;
import tools.threadpool.core.entity.CoreThread;
import tools.threadpool.core.entity.SupportThread;
import tools.threadpool.core.reject.AbstractRejectHandler;

public class CustomThreadPoolExecutor {

    private final BlockingQueue<Runnable> taskQueue;
    private final List<CoreThread> coreWorkQueue;
    private final List<SupportThread> supportWorkQueue;
    private final int coreSize;
    private final int maxSize;
    private final int timeout;
    private final TimeUnit timeUnit;
    private final AbstractRejectHandler rejectHandler;

    public CustomThreadPoolExecutor(final BlockingQueue<Runnable> taskQueue,
        final List<CoreThread> coreWorkQueue,
        final List<SupportThread> supportWorkQueue, final int coreSize, final int maxSize,
        final int timeout, final TimeUnit timeUnit,
        final AbstractRejectHandler rejectHandler) {
        this.taskQueue = taskQueue;
        this.coreWorkQueue = coreWorkQueue;
        this.supportWorkQueue = supportWorkQueue;
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.rejectHandler = rejectHandler;
    }

    public void execute(Runnable task){
        if(coreWorkQueue.size() < coreSize){
            CoreThread thread = new CoreThread(taskQueue);
            coreWorkQueue.add(thread);
            thread.start();
        }

        if(taskQueue.offer(task)){
            return;
        }

        if(coreWorkQueue.size() + supportWorkQueue.size() < maxSize){
            SupportThread thread = new SupportThread(timeout, timeUnit, taskQueue);
            supportWorkQueue.add(thread);
            thread.start();
        }

        if(!taskQueue.offer(task)){
            rejectHandler.rejectExecute(task,this);
        }

    }


    public <T> Future<T> submit(Callable<T> task){
        FutureTask<T> futureTask = new FutureTask<>(task);
        if(coreWorkQueue.size() < coreSize){
            CoreThread thread = new CoreThread(taskQueue);
            coreWorkQueue.add(thread);
            thread.start();
        }

        if(taskQueue.offer(futureTask)){
            return futureTask;
        }

        if(coreWorkQueue.size() + supportWorkQueue.size() < maxSize){
            SupportThread thread = new SupportThread(timeout, timeUnit, taskQueue);
            supportWorkQueue.add(thread);
            thread.start();
        }

        if(!taskQueue.offer(futureTask)){
            rejectHandler.rejectSubmit(task,this);
        }
        return futureTask;
    }

     public void discard(){
        this.taskQueue.poll();
    }

}
