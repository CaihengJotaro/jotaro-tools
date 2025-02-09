package tools.threadpool.core.reject;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import tools.threadpool.core.CustomThreadPoolExecutor;

public class MainThreadReject extends AbstractRejectHandler{

    @Override
    public void rejectExecute(final Runnable task,
        final CustomThreadPoolExecutor threadPoolExecutor) {
        task.run();
    }

    @Override
    public <T> Future<T> rejectSubmit(final Callable<T> task,
        final CustomThreadPoolExecutor threadPoolExecutor) {
        FutureTask<T>futureTask = new FutureTask(task);
        Thread thread = new Thread(futureTask);
        thread.start();
        return futureTask;

    }
}
