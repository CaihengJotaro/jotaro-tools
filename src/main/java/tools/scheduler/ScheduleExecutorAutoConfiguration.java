package tools.scheduler;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {ScheduleExecutorAutoConfiguration.class})
public class ScheduleExecutorAutoConfiguration {

}
