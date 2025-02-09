package tools.threadpool.core.reject;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import tools.threadpool.core.CustomThreadPoolExecutor;

public class ThrowReject extends AbstractRejectHandler{

    @Override
    public void rejectExecute(final Runnable task,
        final CustomThreadPoolExecutor threadPoolExecutor) {
        throw new RuntimeException("任务队列已满");
    }

    @Override
    public <T> Future<T> rejectSubmit(final Callable<T> task,
        final CustomThreadPoolExecutor threadPoolExecutor) {
        throw new RuntimeException("任务队列已满");
    }
}
