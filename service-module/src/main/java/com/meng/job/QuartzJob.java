package com.meng.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String info = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("info");
        System.out.println("quartz job ,info:+++++++++++++" + info + "||" + System.currentTimeMillis());
    }
}
