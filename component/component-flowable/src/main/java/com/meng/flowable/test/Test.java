package com.meng.flowable.test;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Test {

    @Autowired
    RepositoryService repositoryService;
    /**
         * 查询流程定义列表, 涉及到 act_re_procdef表，部署成功会新增记录
         */
    //@Test
    public void testProcessDefinition() {
        List<ProcessDefinition> processList = repositoryService.createProcessDefinitionQuery().list();
        for(ProcessDefinition processDefinition : processList){
            log.info("ProcessDefinition name = {},deploymentId = {}", processDefinition.getName(), processDefinition.getDeploymentId());
        }
    }


}
