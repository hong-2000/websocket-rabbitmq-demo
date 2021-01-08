package com.demo.clientparam.config;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * @author hong-2000
 * @version 1.0.0
 * @description WebSocketConfig配置类
 * @create 2020/11/17 14:00
 */
@Slf4j
@Component
public class WebSocketConfigParam {
    @Bean
    public WebSocketClient webSocketClient() {
        try {
            String name = "demoData";
            //String url = "ws://localhost:7017/websocket/";
            String url = "ws://localhost:8018/websocket/"+name;
            WebSocketClient webSocketClient = new WebSocketClient(new URI(url),new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake handShakeData) {
                    log.info("[websocket] "+name+"连接成功");
                }

                @Override
                public void onMessage(String message) {
                    log.info("[websocket] 收到消息={}",message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    log.info("[websocket] 退出连接");
                }

                @Override
                public void onError(Exception ex) {
                    log.info("[websocket] 连接错误={}",ex.getMessage());
                }
            };
            webSocketClient.connect();
            return webSocketClient;
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }
}
