package com.meng.quartz.config;

import com.meng.quartz.listener.QuartzSchedulerListener;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * quartz config
 *
 * @author : sunyuecheng
 */
@EnableScheduling
@EnableAsync
@Configuration
@PropertySource(value = "file:${config.dir}/config/quartz.properties", ignoreResourceNotFound = true)
public class QuartzConfig {
    private final static int DEFAULT_QUARTZ_THREADPOOL_MAX_POOL_SIZE = 500;

    @Value("${quartz.threadpool.corePoolSize:100}")
    private Integer corePoolSize = 1;

    @Value("${quartz.threadpool.maxPoolSize:500}")
    private Integer maxPoolSize = DEFAULT_QUARTZ_THREADPOOL_MAX_POOL_SIZE;

    @Value("${quartz.threadpool.keepAliveSeconds:60}")
    private Integer keepAliveSeconds = 0;

    @Value("${quartz.threadpool.queueCapacity:100}")
    private Integer queueCapacity = Integer.MAX_VALUE;

    /**
     * job factory
     *
     * @return com.projn.alps.module.manager.JobFactory :
     */
    @Bean
    public JobFactory jobFactory() {
        return new JobFactory();
    }

    /**
     * scheduler
     *
     * @return org.quartz.Scheduler :
     * @throws Exception :
     */
    @Bean
    public Scheduler scheduler() throws Exception {

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory());
        schedulerFactoryBean.setTaskExecutor(schedulerTaskExecutor());
        schedulerFactoryBean.afterPropertiesSet();

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.getListenerManager().addSchedulerListener(new QuartzSchedulerListener());
        return scheduler;
    }

    /**
     * task scheduler
     *
     * @return org.springframework.scheduling.TaskScheduler :
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(maxPoolSize);
        taskScheduler.initialize();
        return taskScheduler;
    }

    /**
     * scheduler task executor
     *
     * @return ThreadPoolTaskExecutor :
     */
    @Bean("schedulerTaskExecutor")
    public ThreadPoolTaskExecutor schedulerTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        return threadPoolTaskExecutor;
    }
}
