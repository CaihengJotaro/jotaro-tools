package springAnno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import tools.threadpool.ThreadPoolAutoConfiguration;

@Retention(RetentionPolicy.RUNTIME)  // 运行时可用
@Target({ElementType.TYPE})
@ComponentScan(basePackageClasses = ThreadPoolAutoConfiguration.class)
public @interface EnableThreadPool {

}
