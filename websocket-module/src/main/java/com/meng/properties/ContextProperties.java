package com.meng.properties;

import com.meng.msg.response.HttpErrorResponseMsgInfo;
import com.meng.widget.WsSessionInfoMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.meng.define.ContextDefine.*;


/**
 * context properties
 *
 * @author : sunyuecheng
 */
@ConfigurationProperties
@Component("WebContextProperties")
//@Component
@PropertySource(value = {"file:${config.dir}/config/ws.properties"}, ignoreResourceNotFound = true)
public class ContextProperties implements InitializingBean {

    @Value("${system.maxHttpResponseWaitSeconds:30}")
    private int maxHttpResponseWaitSeconds = MAX_HTTP_RESPONSE_WAIT_SECONDS;

    @Value("${websocket.context-path:}")
    private String websocketContextPath = null;

    @Value("${system.wsSessionTimeOutMinutes:30}")
    private int wsSessionTimeOutMinutes = DEFAULT_WEBSOCKET_SESSION_TIMEOUT_INTERVAL_MINUTES;

    @Value("${system.maxWsSessionCount:1000}")
    private int maxWsSessionCount = DEFAULT_MAX_WEBSOCKET_SESSION_COUNT;

    @Value("${system.ws.msg.ids:}")
    private String wsMsgIds = null;

    private List<String> wsMsgIdList = null;

    @Value("${system.context.web.exception.response:}")
    private String webExceptionResponse = null;

    private Class webExceptionResponseClass = null;

    /**
     * run time properties
     */
    public ContextProperties() {

    }

    public int getMaxHttpResponseWaitSeconds() {
        return maxHttpResponseWaitSeconds;
    }

    public void setMaxHttpResponseWaitSeconds(int maxHttpResponseWaitSeconds) {
        this.maxHttpResponseWaitSeconds = maxHttpResponseWaitSeconds;
    }

    public String getWebsocketContextPath() {
        return websocketContextPath;
    }

    public void setWebsocketContextPath(String websocketContextPath) {
        this.websocketContextPath = websocketContextPath;
    }

    public int getWsSessionTimeOutMinutes() {
        return wsSessionTimeOutMinutes;
    }

    public void setWsSessionTimeOutMinutes(int wsSessionTimeOutMinutes) {
        this.wsSessionTimeOutMinutes = wsSessionTimeOutMinutes;
    }

    public int getMaxWsSessionCount() {
        return maxWsSessionCount;
    }

    public void setMaxWsSessionCount(int maxWsSessionCount) {
        this.maxWsSessionCount = maxWsSessionCount;
    }

    public String getWsMsgIds() {
        return wsMsgIds;
    }

    public void setWsMsgIds(String wsMsgIds) {
        this.wsMsgIds = wsMsgIds;
    }

    public List<String> getWsMsgIdList() {
        return wsMsgIdList;
    }

    public void setWsMsgIdList(List<String> wsMsgIdList) {
        this.wsMsgIdList = wsMsgIdList;
    }

    public String getWebExceptionResponse() {
        return webExceptionResponse;
    }

    public void setWebExceptionResponse(String webExceptionResponse) {
        this.webExceptionResponse = webExceptionResponse;
    }

    public Class getWebExceptionResponseClass() {
        return webExceptionResponseClass;
    }

    public void setWebExceptionResponseClass(Class webExceptionResponseClass) {
        this.webExceptionResponseClass = webExceptionResponseClass;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StringUtils.isEmpty(wsMsgIds)) {
            wsMsgIdList = Arrays.asList(wsMsgIds.split(","));
        }

        WsSessionInfoMap.getInstance().setMaxPoolSize(maxWsSessionCount);

        if (!StringUtils.isEmpty(webExceptionResponse)) {
            webExceptionResponseClass = Class.forName(webExceptionResponse);
            if (webExceptionResponseClass == null) {
                throw new Exception("Invalid web exception responese");
            }
        } else {
            webExceptionResponseClass = HttpErrorResponseMsgInfo.class;
        }
    }
}
