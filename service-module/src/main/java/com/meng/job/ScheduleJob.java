package com.meng.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 定时任务实现方式：
 1 等待线程，使用while（true）一直循环，不推荐使用
 2 使用Java自带的Timer类进行设计，如下：
 TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("TimerTask is called!");
            }
        };

        Timer timer = new Timer();
        /*
         * schedule 和 scheduleAtFixedRate 区别：
         *  可将schedule理解为scheduleAtFixedDelay，
         *  两者主要区别在于delay和rate
         *  1、schedule，如果第一次执行被延时（delay），
         *      随后的任务执行时间将以上一次任务实际执行完成的时间为准
         *  2、scheduleAtFixedRate，如果第一次执行被延时（delay），
         *      随后的任务执行时间将以上一次任务开始执行的时间为准（需考虑同步）
         *
         *  参数：1、任务体    2、延时时间（可以指定执行日期）3、任务执行间隔时间
         *
        timer.schedule(task, 0, 1000 * 3);
        timer.scheduleAtFixedRate(task, 0, 1000 * 3);
 3 使用juc中的线程工具类设计创建，如下：
 Runnable runnable = new Runnable() {
            public void run() {
                System.out.println("ScheduledExecutorService Task is called!");
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、间隔时间单位
        service.scheduleAtFixedRate(runnable, 0, 3, TimeUnit.SECONDS);
 4 通过springboot的注解设计实现：
 使用注解： @EnableScheduling
          @Scheduled(cron = "0/5 * * * * ?")
          @EnableAsync
          @Async
 5 使用quarts设计动态定时任务实现：


 */
//@Component
//@EnableAsync
@Slf4j
public class ScheduleJob {

     /**
     * execute
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void execute() {
        System.out.println("job info ---------");
    }

    @Async // 使用线程执行任务，比慢当前任务过长或者执行频率过快导致的任务丢失
    @Scheduled(cron = "0/2 * * * * ?")
    public void scheduleMethod(){
        System.out.println("定时器被触发" + new Date());
        log.info("a-------");
    }

}
