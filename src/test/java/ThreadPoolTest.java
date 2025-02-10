import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import tools.threadpool.core.CustomThreadPoolExecutor;
import tools.threadpool.core.reject.ThrowReject;

public class ThreadPoolTest {

    @Test
    public void test() {
        CustomThreadPoolExecutor executor = CustomThreadPoolExecutor
            .builder()
            .coreSize(5)
            .maxSize(10)
            .rejectHandler(new ThrowReject())
            .timeUnit(TimeUnit.SECONDS)
            .timeout(10)
            .taskQueue(new LinkedBlockingQueue<>(10))
            .coreWorkQueue(new ArrayList<>())
            .supportWorkQueue(new ArrayList<>())
            .build();

        for(int i = 0; i < 10; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getId());
            });
        }


    }

}
