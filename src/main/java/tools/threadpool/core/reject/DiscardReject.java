package tools.threadpool.core.reject;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import tools.threadpool.core.CustomThreadPoolExecutor;

public class DiscardReject extends AbstractRejectHandler{

    @Override
    public void rejectExecute(final Runnable task,
        final CustomThreadPoolExecutor threadPoolExecutor) {
        threadPoolExecutor.discard();
        threadPoolExecutor.execute(task);
    }

    @Override
    public <T>Future<T> rejectSubmit(final Callable<T>task,
        final CustomThreadPoolExecutor threadPoolExecutor) {
        threadPoolExecutor.discard();
        return threadPoolExecutor.submit(task);
    }
}
