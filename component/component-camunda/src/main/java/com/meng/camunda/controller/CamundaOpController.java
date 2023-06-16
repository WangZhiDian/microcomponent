package com.meng.camunda.controller;

import com.meng.bean.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.camunda.bpm.engine.RuntimeService;

import java.util.List;

/**
 *
 *
 *
 */
@Slf4j
@RestController
public class CamundaOpController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @GetMapping("/deploy")
    public String deplopy(){
        Deployment deploy = repositoryService.createDeployment()
                .name("部署的第一个流程") // 定义部署文件的名称
                .addClasspathResource("process.bpmn") // 绑定需要部署的流程文件
                .deploy();// 部署流程
        return deploy.getId() + ":" + deploy.getName();
    }


    @GetMapping(value = "/camunda/start")
    public ApiResult<Object> deal(@RequestParam("cid") String cid,
                                  @RequestParam("key") String key) {

        // 部署流程
        //ProcessInstance processInstance = runtimeService.startProcessInstanceById(cid);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        //processInstance.get
        // 部署的流程实例的相关信息
        System.out.println("processInstance.getId() = " + processInstance.getId());
        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());

        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
        for (Task task: list) {
            System.out.println("task id:" + task.getId());
        }

        // 查看历史
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
            .processInstanceId(processInstance.getId())
            .finished()
            .orderByHistoricActivityInstanceEndTime().asc()
            .list();
        for (HistoricActivityInstance activity : activities) {
            System.out.println("act:" + activity.getActivityName());
        }

        return ApiResult.success("suc");
    }

    @GetMapping("/exec_task")
    public String execTask(@RequestParam("taskid") String taskid){
        Task task = taskService.createTaskQuery().taskId(taskid).singleResult();
        if (task == null) {
            System.out.println("task is not exist,task_id:" + taskid);
            return "task is not exist";
        }
        taskService.complete(taskid);
        // 查看历史
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
            .processInstanceId(task.getProcessInstanceId())
            .finished()
            .orderByHistoricActivityInstanceEndTime().asc()
            .list();
        for (HistoricActivityInstance activity : activities) {
            System.out.println("act:" + activity.getActivityName());
        }

        return "through";
    }

    @GetMapping("/deploy_mine")
    public String run_mytest(){
        Deployment deploy = repositoryService.createDeployment()
                .name("部署我的测试") // 定义部署文件的名称
                .addClasspathResource("my-test.bpmn") // 绑定需要部署的流程文件
                .deploy();// 部署流程

        System.out.println(deploy.getId() + ":" + deploy.getName());

        //ProcessInstance processInstance = runtimeService.startProcessInstanceById(deploy.getId());
        String key = "my-test";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);

        System.out.println("processInstance.getId() = " + processInstance.getId());
        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());

        return "suc";
    }

    @PostMapping(value = "/camunda/login")
    public ApiResult<Object> login() {


        return ApiResult.success("login suc");
    }


}
