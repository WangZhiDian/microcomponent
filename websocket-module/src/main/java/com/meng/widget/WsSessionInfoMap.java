package com.meng.widget;

import com.meng.struct.WebSocketSessionInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.meng.define.CommonDefine.MILLI_SECOND_1000;
import static com.meng.define.CommonDefine.ONE_MINUTE_SECOND;


/**
 * web socket session info map
 *
 * @author : sunyuecheng
 */
@Component
public class WsSessionInfoMap {
    private final static int WS_SESSION_INIT_SIZE = 1000;

    private int maxPoolSize = WS_SESSION_INIT_SIZE;
    private Map<String, WebSocketSessionInfo> webSocketSessionInfoMap
            = new ConcurrentHashMap<String, WebSocketSessionInfo>(WS_SESSION_INIT_SIZE);

    private static volatile WsSessionInfoMap instance;

    /**
     * get instance
     *
     * @return WebSocketSessionInfoMap :
     */
    public static WsSessionInfoMap getInstance() {
        if (instance == null) {
            instance = new WsSessionInfoMap();
        }
        return instance;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getPoolSize() {
        return webSocketSessionInfoMap.size();
    }

    /**
     * check pool status
     *
     * @return boolean :
     */
    public boolean checkPoolStatus() {
        return webSocketSessionInfoMap.size() <= maxPoolSize;
    }

    /**
     * add web socket session info
     *
     * @param agentId :
     * @param session :
     * @return boolean :
     * @throws Exception :
     */
    public boolean addWebSocketSessionInfo(String agentId, WebSocketSession session) throws Exception {
        if (session == null || StringUtils.isEmpty(agentId)) {
            return false;
        }

        if (webSocketSessionInfoMap.size() >= maxPoolSize) {
            return false;
        }
        if (webSocketSessionInfoMap.containsKey(agentId)) {
            WebSocketSessionInfo webSocketSessionInfo = webSocketSessionInfoMap.get(agentId);
            if (webSocketSessionInfo != null && webSocketSessionInfo.getSession() != null
                    && webSocketSessionInfo.getSession().isOpen()) {
                webSocketSessionInfo.getSession().close();
            }
        }
        webSocketSessionInfoMap.put(agentId, new WebSocketSessionInfo(session));
        return true;
    }

    public boolean existSessionById(String agentId) throws Exception {
        return webSocketSessionInfoMap.containsKey(agentId);
    }

    /**
     * get web socket session
     *
     * @param agentId :
     * @return WebSocketSession :
     */
    public WebSocketSession getWebSocketSession(String agentId) {
        if (agentId == null) {
            return null;
        }

        WebSocketSessionInfo webSocketSessionInfo = webSocketSessionInfoMap.get(agentId);
        if (webSocketSessionInfo == null) {
            return null;
        }
        WebSocketSession session = webSocketSessionInfo.getSession();
        return session;
    }

    /**
     * get web socket session info
     *
     * @param agentId :
     * @return WebSocketSessionInfo :
     */
    public WebSocketSessionInfo getWebSocketSessionInfo(String agentId) {
        if (agentId == null) {
            return null;
        }
        return webSocketSessionInfoMap.get(agentId);
    }

    /**
     * get web socket session agent id list
     *
     * @return List<String> :
     */
    public List<String> getWebSocketSessionAgentIdList() {
        List<String> agentIdList = new ArrayList<>();
        agentIdList.addAll(webSocketSessionInfoMap.keySet());
        return agentIdList;
    }

    /**
     * update web socket session last receive data time info
     *
     * @param agentId :
     */
    public void updateWebSocketSessionLastReceiveDataTimeInfo(String agentId) {
        if (agentId == null) {
            return;
        }

        WebSocketSessionInfo webSocketSessionInfo = webSocketSessionInfoMap.get(agentId);
        if (webSocketSessionInfo == null) {
            return;
        }
        webSocketSessionInfo.setLastReceiveDataTime(System.currentTimeMillis());
    }

    /**
     * update web socket session last send data time info
     *
     * @param agentId :
     */
    public void updateWebSocketSessionLastSendDataTimeInfo(String agentId) {
        if (agentId == null) {
            return;
        }
        WebSocketSessionInfo webSocketSessionInfo = webSocketSessionInfoMap.get(agentId);
        if (webSocketSessionInfo == null) {
            return;
        }
        webSocketSessionInfo.setLastSendDataTime(System.currentTimeMillis());
    }

    /**
     * void remove web socket session info
     *
     * @param agentId :
     * @throws IOException :
     */
    public void removeWebSocketSessionInfo(String agentId) throws IOException {
        if (StringUtils.isEmpty(agentId)) {
            return;
        }

        WebSocketSessionInfo webSocketSessionInfo = webSocketSessionInfoMap.get(agentId);
        if (webSocketSessionInfo == null) {
            return;
        }

        WebSocketSession session = webSocketSessionInfo.getSession();
        if (session != null && session.isOpen()) {
            session.close();
        }
        webSocketSessionInfoMap.remove(agentId);
    }

    /**
     * remove invalid web socket session info
     *
     * @param waitTimeOutMinutes :
     * @return List<String> :
     * @throws IOException :
     */
    public List<String> removeInvalidWebSocketSessionInfo(int waitTimeOutMinutes) throws IOException {
        if (waitTimeOutMinutes <= 0) {
            return null;
        }
        Long currentDateTime = System.currentTimeMillis();

        List<String> agentIdList = new ArrayList<String>();
        for (Map.Entry<String, WebSocketSessionInfo> entry : webSocketSessionInfoMap.entrySet()) {
            WebSocketSessionInfo webSocketSessionInfo = entry.getValue();
            if (webSocketSessionInfo == null || webSocketSessionInfo.getSession() == null) {
                agentIdList.add(entry.getKey());
                continue;
            }
            if (currentDateTime - webSocketSessionInfo.getLastReceiveDataTime()
                    >= waitTimeOutMinutes * ONE_MINUTE_SECOND * MILLI_SECOND_1000) {
                if (webSocketSessionInfo.getSession() != null && webSocketSessionInfo.getSession().isOpen()) {
                    webSocketSessionInfo.getSession().close();
                }
                agentIdList.add(entry.getKey());
            }
        }

        for (String agentId : agentIdList) {
            webSocketSessionInfoMap.remove(agentId);
        }
        return agentIdList;
    }

    /**
     * remove all web socket session info
     *
     * @return List<String> :
     * @throws IOException :
     */
    public List<String> removeAllWebSocketSessionInfo() throws IOException {
        List<String> agentIdList = new ArrayList<String>();
        for (Map.Entry<String, WebSocketSessionInfo> entry : webSocketSessionInfoMap.entrySet()) {
            WebSocketSessionInfo webSocketSessionInfo = entry.getValue();
            if (webSocketSessionInfo != null && webSocketSessionInfo.getSession() != null
                    && webSocketSessionInfo.getSession().isOpen()) {
                webSocketSessionInfo.getSession().close();
            }
            agentIdList.add(entry.getKey());
        }
        webSocketSessionInfoMap.clear();
        return agentIdList;
    }

    /**
     * send web socket message info
     *
     * @param agentId :
     * @param msg     :
     * @throws Exception :
     */
    public void sendWebSocketMessageInfo(String agentId, String msg) throws Exception {
        if (StringUtils.isEmpty(agentId) || StringUtils.isEmpty(msg)) {
            return;
        }
        if (webSocketSessionInfoMap.get(agentId) != null) {
            WebSocketSession session = webSocketSessionInfoMap.get(agentId).getSession();
            if (session != null && session.isOpen()) {
                TextMessage message = new TextMessage(msg);
                session.sendMessage(message);
                updateWebSocketSessionLastSendDataTimeInfo(agentId);
            }
        }
    }

    /**
     * send all web socket message info
     *
     * @param msg      :
     * @param interval :
     * @throws Exception :
     */
    public void sendAllWebSocketMessageInfo(String msg, long interval) throws Exception {
        if (StringUtils.isEmpty(msg) || interval < 0) {
            return;
        }
        for (Map.Entry<String, WebSocketSessionInfo> entry : webSocketSessionInfoMap.entrySet()) {
            WebSocketSessionInfo webSocketSessionInfo = entry.getValue();
            if (webSocketSessionInfo != null && webSocketSessionInfo.getSession() != null
                    && webSocketSessionInfo.getSession().isOpen()) {
                TextMessage message = new TextMessage(msg);
                webSocketSessionInfo.getSession().sendMessage(message);
                updateWebSocketSessionLastSendDataTimeInfo(entry.getKey());
                Thread.sleep(interval);
            }
        }
    }
}
