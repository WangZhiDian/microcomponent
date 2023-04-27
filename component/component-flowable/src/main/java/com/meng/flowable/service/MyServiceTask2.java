package com.meng.flowable.service;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component(value = "myServiceTask2")
public class MyServiceTask2 implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) {

        System.out.println("getEventName() = " + delegateExecution.getEventName());
        System.out.println("========MyServiceTask==========");
        log.info("service2 get math_param:{}", delegateExecution.getVariable("math_param"));
        int num = (int)(Math.random() * 100);
        log.info("service2 new math num:{}", num);
        delegateExecution.setVariable("math_param2", num);
        log.info("thread name:{},id:{}", Thread.currentThread().getName(),Thread.currentThread().getId());
    }
}
