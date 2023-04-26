package com.meng.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meng.widget.WsSessionInfoMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;


/**
 * http controller tools
 *
 * @author : sunyuecheng
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "system.context.switch.websocket", havingValue = "true", matchIfMissing = true)
public class WsControllerTools {
    private static final Logger LOGGER = LoggerFactory.getLogger(WsControllerTools.class);

/*
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
*/

    /**
     * deal
     *
     * @param session :
     * @param message :
     * @return boolean :
     */
    public boolean deal(WebSocketSession session, TextMessage message) throws Exception {
        if (session == null || message == null || StringUtils.isEmpty(message.getPayload())) {
            LOGGER.error("Invalid param.");
            return false;
        }
        String sessionId = session.getId();
        log.info("session id:{}", sessionId);

        if (!StringUtils.isEmpty(sessionId) && !WsSessionInfoMap.getInstance().existSessionById(sessionId)) {
            WsSessionInfoMap.getInstance().addWebSocketSessionInfo(sessionId, session);
            log.info("add session suc, session id:{}", sessionId);
        }

        String textMsg = message.getPayload();
        LOGGER.debug("Request data({}).", textMsg);
        log.info("Request data({}).", textMsg);
        String agentId = (String) session.getAttributes().get("agent_id");
        log.info("agent_id:{}", agentId);

/*
        try {
            WsRequestMsgInfo wsRequestMsgInfo =
                    JSONObject.parseObject(textMsg, WsRequestMsgInfo.class);
            if (wsRequestMsgInfo == null) {
                throw new Exception("Invalid web socket request msg,msg info(" + message.toString() + ").");
            }

            RequestServiceInfo requestServiceInfo = getRequestServiceInfo(wsRequestMsgInfo.getMsgId());

            Boolean authOk = (Boolean) session.getAttributes().get(AUTH_OK_KEY);
            String agentId = (String) session.getAttributes().get(AGENT_ID_KEY);
            if(authOk == null || !authOk || StringUtils.isEmpty(agentId)) {
                if (requestServiceInfo.getAuthorizationFilter() != null) {
                    if (!requestServiceInfo.getAuthorizationFilter().auth(session, requestServiceInfo.getUserRoleNameList())) {
                        return false;
                    }
                    agentId = (String) session.getAttributes().get(AGENT_ID_KEY);
                    if (StringUtils.isEmpty(agentId)) {
                        return false;
                    }
                    session.getAttributes().put(AUTH_OK_KEY, true);
                    if (!WsSessionInfoMap.getInstance().addWebSocketSessionInfo(agentId, session)) {
                        LOGGER.error("Add web socket session to pool error, pool size({}).",
                                WsSessionInfoMap.getInstance().getPoolSize());
                        return false;
                    }
                } else {
                    return false;
                }
            }
            wsRequestMsgInfo.setAgentId(agentId);

            WsRequestInfo wsRequestInfo = null;
            if (requestServiceInfo.getParamClass() != null) {
                String msgBodyText = JSON.toJSONString(wsRequestMsgInfo.getMsg());
                wsRequestInfo = RequestInfoUtils.convertWsRequestInfo(session, msgBodyText,
                        requestServiceInfo.getParamClass());
                wsRequestInfo.getExtendInfoMap().put(MSG_ID_KEY, wsRequestMsgInfo.getMsgId());

                if (wsRequestInfo != null && wsRequestInfo.getParamObj() != null) {
                    ParamCheckUtils.checkParam(wsRequestInfo.getParamObj());
                }
            }

            if (threadPoolTaskExecutor.getActiveCount() < threadPoolTaskExecutor.getMaxPoolSize()) {
                threadPoolTaskExecutor.execute(new WsProcessWorker(requestServiceInfo.getServiceName(),
                        wsRequestInfo, session));
            } else {
                LOGGER.debug("System is busy.");
            }
        } catch (Exception e) {
            LOGGER.error("Analyse request info error,msg info({}),error info({}).",
                    message.toString(), formatExceptionInfo(e));
            return false;
        }
*/

        return true;
    }

/*    private RequestServiceInfo getRequestServiceInfo(String uri) throws Exception {
        Map<String, List<RequestServiceInfo>> requestServiceInfoMap = null;

        for (Map.Entry<String, Map<String, List<RequestServiceInfo>>> requestServiceInfoEntry
                : ServiceData.getRequestServiceInfoMap().entrySet()) {
            String key = requestServiceInfoEntry.getKey();
            if (key.equals(uri) || uri.matches(key)) {
                requestServiceInfoMap = requestServiceInfoEntry.getValue();
                break;
            }
        }

        if (requestServiceInfoMap == null || requestServiceInfoMap.isEmpty()
                || requestServiceInfoMap.get(HTTP_METHOD_POST.toLowerCase()) == null) {
            throw new Exception("Invalid request service info, msg id("
                    + uri + "), method(" + HTTP_METHOD_POST + ").");
        }

        List<RequestServiceInfo> requestServiceInfoList = requestServiceInfoMap.get(HTTP_METHOD_POST.toLowerCase());
        if (requestServiceInfoList.size() != 1) {
            LOGGER.error("Invalid request service info, msg id({}), method({}).",
                    uri, HTTP_METHOD_POST);
            throw new HttpException(HttpStatus.BAD_REQUEST.value(), RESULT_INVALID_REQUEST_INFO_ERROR);
        }
        RequestServiceInfo requestServiceInfo = requestServiceInfoList.get(0);

        if (!requestServiceInfo.getType().equalsIgnoreCase(RequestServiceInfo.SERVICE_TYPE_WS)) {
            throw new Exception("Invalid request service type info, type(" + requestServiceInfo.getType() + ").");
        }

        return requestServiceInfo;
    }*/

}
