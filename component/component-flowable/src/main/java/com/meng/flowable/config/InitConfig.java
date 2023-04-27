package com.meng.flowable.config;


import com.meng.flowable.listener.FlowableBaseEventListenerImpl;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Configuration
public class InitConfig implements CommandLineRunner {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private FlowableBaseEventListenerImpl flowableBaseEventListener;

    @Override
    public void run(String... args) {
        runtimeService.addEventListener(flowableBaseEventListener);
    }
}
