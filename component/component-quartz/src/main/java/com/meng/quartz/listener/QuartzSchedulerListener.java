package com.meng.quartz.listener;

import com.alibaba.fastjson.JSON;
import com.meng.util.CommonUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * quartz scheduler listener
 *
 * @author : s00441405
 */
public class QuartzSchedulerListener implements SchedulerListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzSchedulerListener.class);

    @Override
    public void jobScheduled(Trigger trigger) {

    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        LOGGER.error("Find unscheduled job, trigger info({}).", JSON.toJSONString(triggerKey));
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        LOGGER.error("Find finalized job, trigger info({}).", JSON.toJSONString(trigger.getKey()));
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        LOGGER.error("Find paused job, trigger info({}).", JSON.toJSONString(triggerKey));
    }

    @Override
    public void triggersPaused(String triggerGroup) {
        LOGGER.error("Find paused triggers, trigger info({}).", triggerGroup);
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
    }

    @Override
    public void triggersResumed(String triggerGroup) {
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {

    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        LOGGER.error("Find deleted job, job info({}).", JSON.toJSONString(jobKey));
    }

    @Override
    public void jobPaused(JobKey jobKey) {

    }

    @Override
    public void jobsPaused(String jobGroup) {

    }

    @Override
    public void jobResumed(JobKey jobKey) {

    }

    @Override
    public void jobsResumed(String jobGroup) {

    }

    @Override
    public void schedulerInStandbyMode() {

    }

    @Override
    public void schedulerStarted() {

    }

    @Override
    public void schedulerStarting() {

    }

    @Override
    public void schedulerShutdown() {

    }

    @Override
    public void schedulerShuttingdown() {

    }

    @Override
    public void schedulingDataCleared() {

    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {
        LOGGER.error("Find scheduler error, msg({}), error info({}).", msg, CommonUtils.formatExceptionInfo(cause));
    }
}
