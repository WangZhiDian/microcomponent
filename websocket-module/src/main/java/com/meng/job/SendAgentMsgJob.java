package com.meng.job;

import com.alibaba.fastjson.JSON;
import com.meng.widget.WsSessionInfoMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * send agent msg job
 *
 * @author : sunyuecheng
 */
@Component
@ConditionalOnProperty(name = "system.context.switch.websocket", havingValue = "true", matchIfMissing = false)
public class SendAgentMsgJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendAgentMsgJob.class);

    private static final int DEFAULT_AGENT_ID_LIST_SIZE = 1000;

    /*@Autowired
    private SendAgentMsgTools sendAgentMsgTools;*/

    /**
     * execute
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void execute() throws Exception {
        List<String> agentIdList = WsSessionInfoMap.getInstance().getWebSocketSessionAgentIdList();
        LOGGER.info("Scan agent msg info, agent online num({}).", agentIdList.size());

        String backinfo = "back info:" + System.currentTimeMillis();
        WsSessionInfoMap.getInstance().sendAllWebSocketMessageInfo(backinfo, 5000L);

        /*long pageCount = agentIdList.size() / DEFAULT_AGENT_ID_LIST_SIZE;
        if (agentIdList.size() % DEFAULT_AGENT_ID_LIST_SIZE != 0) {
            pageCount++;
        }

        for (long i = 0; i < pageCount; i++) {
            long beginIndex = i * DEFAULT_AGENT_ID_LIST_SIZE;
            long endIndex = (i + 1) * DEFAULT_AGENT_ID_LIST_SIZE;
            if (i == pageCount - 1) {
                endIndex = agentIdList.size();
            }
            List<String> tempAgentIdList = agentIdList.subList((int) beginIndex, (int) endIndex);
            sendAgentMsgTools.asyncSendAgentMsgInfo(tempAgentIdList);
        }*/
    }
}
