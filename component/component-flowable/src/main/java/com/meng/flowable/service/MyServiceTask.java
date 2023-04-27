package com.meng.flowable.service;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component(value = "myServiceTask")
public class MyServiceTask  implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) {

        System.out.println("getEventName() = " + delegateExecution.getCurrentActivityId());
        System.out.println("========MyServiceTask==========");
        log.info("get math_param:{}", delegateExecution.getVariable("math_param"));
        log.info("get math_param2:{}", delegateExecution.getVariable("math_param2"));

        int num = (int)(Math.random() * 100);
        log.info("new math num:{}", num);
        delegateExecution.setVariable("math_param", num);
        log.info("thread name:{},id:{}", Thread.currentThread().getName(),Thread.currentThread().getId());
    }
}
