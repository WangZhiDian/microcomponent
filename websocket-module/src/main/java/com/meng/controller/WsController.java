package com.meng.controller;

import com.meng.tool.WsControllerTools;
import com.meng.widget.WsSessionInfoMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static com.meng.define.ContextDefine.AGENT_ID_KEY;
import static com.meng.define.ContextDefine.WEBSOCKET_DEFAULT_BUFFER_SIZE;
import static com.meng.util.CommonUtils.formatExceptionInfo;

/**
 * web socket controller
 *
 * @author : sunyuecheng
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "system.context.switch.websocket", havingValue = "true", matchIfMissing = true)
public class WsController extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WsController.class);

    @Autowired
    private WsControllerTools wsControllerTools;

    /**
     * after connection established
     *
     * @param session :
     * @throws Exception :
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (WsSessionInfoMap.getInstance().getPoolSize() >= WsSessionInfoMap.getInstance().getMaxPoolSize()) {
            session.close();
        }
        session.setTextMessageSizeLimit(WEBSOCKET_DEFAULT_BUFFER_SIZE);
        log.info("session size:{}", WsSessionInfoMap.getInstance().getPoolSize());
    }

    /**
     * after connection closed
     *
     * @param session :
     * @param status  :
     * @throws Exception :
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        try {
            removeWebSocketSessionInfo(session);
        } catch (Exception e) {
            LOGGER.error("Close web socket connection error, error info({}).", formatExceptionInfo(e));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("receive info:{}", message.getPayload());
        if (!wsControllerTools.deal(session, message)) {
            log.info("remove session");
            removeWebSocketSessionInfo(session);
        }
    }

    /**
     * handle transport error
     *
     * @param session :
     * @param error   :
     * @throws Exception :
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable error) throws Exception {
        LOGGER.error("Web socket connection error, error info({}).", formatExceptionInfo(error));
        removeWebSocketSessionInfo(session);
    }

    private void removeWebSocketSessionInfo(WebSocketSession session) {
        if (session == null) {
            return;
        }

        try {
            String agentId = (String) session.getAttributes().get(AGENT_ID_KEY);
            if (!StringUtils.isEmpty(agentId)) {
                LOGGER.info("Remove web socket connection, agent id({}).", agentId);
                WsSessionInfoMap.getInstance().removeWebSocketSessionInfo(agentId);
            } else {
                session.close();
            }
        } catch (Exception e) {
            LOGGER.error("Close web socket connection error,error info({}).", formatExceptionInfo(e));
        }
    }
}