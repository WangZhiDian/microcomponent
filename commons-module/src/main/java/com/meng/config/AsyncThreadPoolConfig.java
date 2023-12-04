package com.meng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 该类为自定义的线程池，配合spring注解Async来使用，该效果等同于直接定义线程池，然后执行runnable方法的worker
 * 优点：Async调用时直接创建一个新的线程来执行异步方法，未使用线程池，在某些量大场景，可能引起效率和资源问题
 * 并且该方式是spring托管的，使用起来方便，但调用该方法不能是同一个类中的方法相互调用，切需要时public的修饰
 */
@Configuration
@EnableAsync
public class AsyncThreadPoolConfig {

    @Bean("serviceTaskExector")// 名称可以随意定，可以设置成和业务相关名称
    public Executor taskExecutor() {
        //通过Runtime方法来获取当前服务器cpu内核，根据cpu内核来创建核心线程数和最大线程数
        int threadCount = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadCount);
        executor.setMaxPoolSize(threadCount);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("serviceTaskExecutor-");

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }


}
