package tools.scheduler.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.LockSupport;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.scheduler.core.ScheduleJob;
import tools.threadpool.core.CustomThreadPoolExecutor;

@Component
@Slf4j
public class ScheduleExecutor {

    private Trigger trigger = new Trigger();

    @Value("${schedule.config.threadpool.coresize:10}")
    private Integer scheduleThreadPoolSize;

    private ExecutorService executor = Executors.newFixedThreadPool(scheduleThreadPoolSize);

    public void schedule(Runnable task, long delay) {
        ScheduleJob job = ScheduleJob
            .builder()
            .delay(delay)
            .startTime(System.currentTimeMillis() + delay)
            .task(task)
            .build();

        trigger.addJob(job);
        trigger.wakeUp();

    }

    @Getter
    class Trigger{
        private final PriorityBlockingQueue<ScheduleJob> scheduleJobs = new PriorityBlockingQueue<>();

        private final Thread thread = new Thread(() -> {
            while (true){
                while (scheduleJobs.isEmpty()){
                    LockSupport.park();
                }
                ScheduleJob job = scheduleJobs.peek();
                if(job.getStartTime() > System.currentTimeMillis()){
                    job = scheduleJobs.poll();
                    if(job.isCancel()){
                        continue;
                    }
                    executor.execute(job.getTask());
                    job.setStartTime(System.currentTimeMillis() + job.getDelay());
                }else {
                    LockSupport.parkUntil(job.getStartTime());
                }

            }
        });

        {
            thread.start();
            log.info("schedule thread started");
        }

        public void wakeUp(){
            LockSupport.unpark(thread);
        }

        public void addJob(ScheduleJob scheduleJob){
            scheduleJobs.add(scheduleJob);
        }

    }

}
