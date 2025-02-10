package tools.scheduler.core;

import java.util.Comparator;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleJob implements Comparator<ScheduleJob> {

    private Runnable task;

    private Long startTime;

    private Long delay;

    private boolean cancel;

    @Override
    public int compare(final ScheduleJob o1, final ScheduleJob o2) {
        return (o1.getStartTime() - o2.getStartTime()) < 0 ? -1 : 1;
    }
}
