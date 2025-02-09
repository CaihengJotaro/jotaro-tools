package tools.threadpool.core.reject;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import tools.threadpool.core.CustomThreadPoolExecutor;

public abstract class AbstractRejectHandler {
    public abstract void rejectExecute(Runnable task, CustomThreadPoolExecutor threadPoolExecutor);

    public abstract <T> Future<T> rejectSubmit(
        Callable<T>task, CustomThreadPoolExecutor threadPoolExecutor);

}
