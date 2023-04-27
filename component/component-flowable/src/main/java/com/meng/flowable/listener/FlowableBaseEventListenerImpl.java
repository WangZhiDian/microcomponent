package com.meng.flowable.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEventType;
import org.flowable.engine.*;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FlowableBaseEventListenerImpl implements FlowableEventListener {

/*    @Autowired
    private FlowableTaskCreatedCallback flowableTaskCreatedCallback;
    @Autowired
    private FlowableTaskCompletedCallback flowableTaskCompletedCallback;
    @Autowired
    private FlowableInstanceFinishedCallback flowableInstanceFinishedCallback;
    @Autowired
    private FlowableInstanceDeletedCallback flowableInstanceDeletedCallback;*/
    //
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Qualifier("processEngine")
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private TaskService taskService;
    @Autowired
    protected ManagementService managementService;
    @Autowired
    protected HistoryService historyService;


    @Override
    public void onEvent(FlowableEvent event) {
        FlowableEventType type = event.getType();
        //log.info("flowable type:{}", type.name());
        if (type == FlowableEngineEventType.TASK_CREATED) {
            // 用户任务创建完成
            if (event instanceof org.flowable.common.engine.impl.event.FlowableEntityEventImpl) {
                org.flowable.common.engine.impl.event.FlowableEntityEventImpl eventImpl = (org.flowable.common.engine.impl.event.FlowableEntityEventImpl) event;
                TaskEntityImpl taskEntity = (TaskEntityImpl) eventImpl.getEntity();
                //requestDTO = flowableTaskCreatedCallback.call(eventImpl, taskEntity);
            }
        } else if (type == FlowableEngineEventType.TASK_COMPLETED) {
            // 用户审批任务
            if (event instanceof org.flowable.engine.delegate.event.impl.FlowableEntityWithVariablesEventImpl) {
                org.flowable.engine.delegate.event.impl.FlowableEntityWithVariablesEventImpl eventImpl = (org.flowable.engine.delegate.event.impl.FlowableEntityWithVariablesEventImpl) event;
                TaskEntityImpl taskEntity = (TaskEntityImpl) eventImpl.getEntity();
                //requestDTO = flowableTaskCompletedCallback.call(eventImpl, taskEntity);
            }
        } else if (type == FlowableEngineEventType.PROCESS_COMPLETED) {
            // 流程完成
            if (event instanceof org.flowable.engine.delegate.event.impl.FlowableEntityEventImpl) {
                org.flowable.engine.delegate.event.impl.FlowableEntityEventImpl eventImpl = (org.flowable.engine.delegate.event.impl.FlowableEntityEventImpl) event;
                ExecutionEntityImpl taskEntity = (ExecutionEntityImpl) eventImpl.getEntity();
                //requestDTO = flowableInstanceFinishedCallback.call(eventImpl, taskEntity);
            }
        } else if (type == FlowableEngineEventType.PROCESS_CANCELLED) {
            // 流程删除
            if (event instanceof org.flowable.engine.delegate.event.impl.FlowableProcessCancelledEventImpl) {
                org.flowable.engine.delegate.event.impl.FlowableProcessCancelledEventImpl eventImpl = (org.flowable.engine.delegate.event.impl.FlowableProcessCancelledEventImpl) event;
                //requestDTO = flowableInstanceDeletedCallback.call(eventImpl, null);
            }
        }
/*        if (requestDTO != null) {
            BpmnModel bpmnModel = repositoryService.getBpmnModel(requestDTO.getProcessDefinitionId());
            Map<String, String> dataObjectMap = bpmnModel.getMainProcess().getDataObjects().stream()
                    .collect(Collectors.toMap(v -> v.getId(), v -> v.getValue() == null ? "" : v.getValue().toString()));
            String businessNotifyUri = dataObjectMap.get(WorkflowConstants.BUSINESS_NOTIFY_URI);
            if (StringUtils.isNotEmpty(businessNotifyUri)) {
                new Thread(() -> {
                	// TODO 以后完善为线程池异步
                    try {
                        Thread.sleep(2000);
                        log.info("sleep 2 s");
                        // 延迟2秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }).start();
            }
        }*/

    }



    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}
