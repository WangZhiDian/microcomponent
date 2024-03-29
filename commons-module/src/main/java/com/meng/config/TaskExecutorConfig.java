package com.meng.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * task executor config
 *
 * @author : sunyuecheng
 */
@Configuration
@PropertySource(value = "file:${config.dir}/config/threadpool.properties", ignoreResourceNotFound = true)
public class TaskExecutorConfig {

    @Value("${threadpool.corePoolSize}")
    private Integer corePoolSize = 1;

    @Value("${threadpool.maxPoolSize}")
    private Integer maxPoolSize = Integer.MAX_VALUE;

    @Value("${threadpool.keepAliveSeconds}")
    private Integer keepAliveSeconds = 0;

    @Value("${threadpool.queueCapacity}")
    private Integer queueCapacity = Integer.MAX_VALUE;

    /**
     * thread pool task executor
     * 该类为spring封装的jdk 线程池ThreadPoolExecutor，spring管理该类，通过注入的方式使用
     * @return org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor :
     */
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix("controllerThreadPool-");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        return threadPoolTaskExecutor;
    }
}
