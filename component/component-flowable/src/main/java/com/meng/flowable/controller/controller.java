package com.meng.flowable.controller;

import com.meng.bean.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class controller {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @GetMapping(value = "/flow/get")
    public ApiResult deal() {

        testProcessDefinition();

        return ApiResult.success("success");
    }

    @GetMapping(value = "/flow/get2")
    public ApiResult deal2() {

        testFlow();

        return ApiResult.success("success");
    }

    /**
     * 查询流程定义列表, 涉及到 act_re_procdef表，部署成功会新增记录
     */
    private void testProcessDefinition() {
        List<ProcessDefinition> processList = repositoryService.createProcessDefinitionQuery().list();
        for(ProcessDefinition processDefinition : processList){
            log.info("ProcessDefinition name = {},deploymentId = {}",
                    processDefinition.getName(), processDefinition.getDeploymentId());
        }
    }

    public void testFlow() {
        // 发起请假
        Map<String, Object> map = new HashMap<>();
        map.put("day", 2);
        map.put("studentUser", "小明");
        ProcessInstance studentLeave = runtimeService.startProcessInstanceByKey("StudentLeave", map);
        Task task = taskService.createTaskQuery().processInstanceId(studentLeave.getId()).singleResult();
        taskService.complete(task.getId());

        // 老师审批
        List<Task> teacherTaskList = taskService.createTaskQuery().taskCandidateGroup("teacher").list();
        Map<String, Object> teacherMap = new HashMap<>();
        teacherMap.put("outcome", "通过");
        for (Task teacherTask : teacherTaskList) {
            taskService.complete(teacherTask.getId(), teacherMap);
        }

        // 校长审批
        List<Task> principalTaskList = taskService.createTaskQuery().taskCandidateGroup("principal").list();
        Map<String, Object> principalMap = new HashMap<>();
        principalMap.put("outcome", "通过");
        for (Task principalTask : principalTaskList) {
            taskService.complete(principalTask.getId(), principalMap);
        }

        // 查看历史
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
            .processInstanceId(studentLeave.getId())
            .finished()
            .orderByHistoricActivityInstanceEndTime().asc()
            .list();
        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityName());
        }
    }
}
