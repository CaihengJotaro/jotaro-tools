package tools.threadpool.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.threadpool.core.entity.AbstractThread;
import tools.threadpool.core.reject.AbstractRejectHandler;
import tools.threadpool.core.reject.DiscardReject;
import tools.threadpool.core.reject.MainThreadReject;
import tools.threadpool.core.reject.ThrowReject;

@Configuration
@ConfigurationProperties(prefix = "threadpool")
@Data
public class ThreadPoolParamConfiguration {

    private Map<String,ThreadPoolConfig> configMap;

    @Data
    class ThreadPoolConfig{
        private int coreSize;
        private int maxSize;
        private int timeout;
        private String timeUnit;
        private String reject;

        public TimeUnit getActualTimeUnit() {
            switch (timeUnit){
                case "sec":
                    return TimeUnit.SECONDS;
                case "min":
                    return TimeUnit.MINUTES;
                case "hour":
                    return TimeUnit.HOURS;
                case "day":
                    return TimeUnit.DAYS;
                default:
                    return TimeUnit.SECONDS;
            }
        }

        public AbstractRejectHandler getActualRejectHandler() {
            switch (reject){
                case "discard":
                    return new DiscardReject();
                case "mainThread":
                    return new MainThreadReject();
                case "throwException":
                    return new ThrowReject();
                default:
                    return new DiscardReject();
            }
        }

    }
}
