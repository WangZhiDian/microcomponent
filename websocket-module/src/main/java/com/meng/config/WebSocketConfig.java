package com.meng.config;

import com.meng.controller.WsController;
import com.meng.handler.HttpSessionHandler;
import com.meng.properties.ContextProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * web socket config
 *
 * @author : sunyuecheng
 */
@Configuration
@EnableWebSocket
@EnableConfigurationProperties(ContextProperties.class)
@ConditionalOnProperty(name = "system.context.switch.websocket", havingValue = "true", matchIfMissing = true)
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    @Qualifier("WebContextProperties")
    private ContextProperties contextProperties;

    /**
     * register web socket handlers
     *
     * @param registry :
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketController(), contextProperties.getWebsocketContextPath()).
                addInterceptors(new HttpSessionHandler()).setAllowedOrigins("*");
    }

    /**
     * web socket controller
     *
     * @return org.springframework.web.socket.WebSocketHandler :
     */
    @Bean
    public WebSocketHandler webSocketController() {
        return new WsController();
    }
}