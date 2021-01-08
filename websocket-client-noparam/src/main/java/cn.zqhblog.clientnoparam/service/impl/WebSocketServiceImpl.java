package cn.zqhblog.clientnoparam.service.impl;

import cn.zqhblog.clientnoparam.service.WebSocketService;
import org.java_websocket.client.WebSocketClient;
import org.springframework.stereotype.Service;

/**
 * @author hong-2000
 * @version 1.0.0
 * @description
 * @create 2020/11/17 15:02
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    private final WebSocketClient webSocketClient;

    public WebSocketServiceImpl(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    @Override
    public void groupSending(String message) {
        String sendMessage = message+"---666";
        webSocketClient.send(sendMessage);
    }

    @Override
    public void appointSending(String name, String message) {
        String sendMessage = "appoint"+name+";"+message;
        // 这里指定发送的规则由服务端决定参数格式
        webSocketClient.send(sendMessage);
    }
}
