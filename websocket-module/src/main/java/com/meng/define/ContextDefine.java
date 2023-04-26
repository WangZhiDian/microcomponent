package com.meng.define;

/**
 * context define
 *
 * @author : sunyuecheng
 */
public final class ContextDefine {

    /**
     * max http response wait seconds
     */
    public static final int MAX_HTTP_RESPONSE_WAIT_SECONDS = 60;

    /**
     * agent id key
     */
    public static final String AGENT_ID_KEY = "AGENT_ID";

    /**
     * auth ok key
     */
    public static final String AUTH_OK_KEY = "AUTH_OK";

    /**
     * websocket default buffer size
     */
    public static final int WEBSOCKET_DEFAULT_BUFFER_SIZE = 64 * 1024;

    /**
     * websocket max buffer size
     */
    public static final int WEBSOCKET_MAX_BUFFER_SIZE = 250 * 1024;

    /**
     * trace context key
     */
    public static final String REMOTE_ADDRESS_KEY = "remoteAddress";

    /**
     * servlet request key
     */
    public static final String SERVLET_REQUEST_KEY = "servletRequest";

    /**
     * servlet response key
     */
    public static final String SERVLET_RESPONSE_KEY = "servletResponse";

    /**
     * msg id key
     */
    public static final String MSG_ID_KEY = "msgId";

    /**
     * default websocket session timeout interval minutes
     */
    public static final int DEFAULT_WEBSOCKET_SESSION_TIMEOUT_INTERVAL_MINUTES = 30;

    /**
     * default websocket session timeout interval minutes
     */
    public static final int DEFAULT_MAX_WEBSOCKET_SESSION_COUNT = 1000;

    /**
     * agent online num key
     */
    public static final String AGENT_ONLINE_NUM_KEY = "agentOnlineNum";

    private ContextDefine() {
    }
}
