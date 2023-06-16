package com.meng.camunda.controller;

import javafx.application.Application;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class CamundaOpControllerTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    /**
     * 启动流程的案例
     */
    @Test
    public void startFlow(){
        // 部署流程
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById("1a880f27-2e57-11ed-80d9-c03c59ad2248");
        // 部署的流程实例的相关信息
        System.out.println("processInstance.getId() = " + processInstance.getId());
        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());

    }



}