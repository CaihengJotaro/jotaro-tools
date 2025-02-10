package tools.threadpool.config;

import java.util.concurrent.ThreadPoolExecutor;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;
import tools.threadpool.config.ThreadPoolParamConfiguration.ThreadPoolConfig;
import tools.threadpool.core.CustomThreadPoolExecutor;

@Component
@AllArgsConstructor
public class ThreadPoolRegistrar implements BeanDefinitionRegistryPostProcessor {

    private final ThreadPoolParamConfiguration threadPoolParamConfiguration;

    @Override
    public void postProcessBeanDefinitionRegistry(
        final BeanDefinitionRegistry beanDefinitionRegistry)
        throws BeansException {
        threadPoolParamConfiguration.getConfigMap().forEach((beanName, threadPoolConfig) -> {
            BeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(CustomThreadPoolExecutor.class)
                .addPropertyValue("coreSize", threadPoolConfig.getCoreSize())
                .addPropertyValue("maxSize", threadPoolConfig.getMaxSize())
                .addPropertyValue("timeout", threadPoolConfig.getTimeout())
                .addPropertyValue("reject", threadPoolConfig.getActualRejectHandler())
                .addPropertyValue("timeUnit", threadPoolConfig.getActualTimeUnit())
                .getBeanDefinition();
            beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
        });
    }

    @Override
    public void postProcessBeanFactory(
        final ConfigurableListableBeanFactory configurableListableBeanFactory)
        throws BeansException {
    }

    private


}
