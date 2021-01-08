package com.demo.servernoparam.server;

import com.demo.servernoparam.mq.SendMessageService;
import com.demo.servernoparam.utils.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hong-2000
 * @version 1.0.0
 * @description WebSocket服务端代码
 * @create 2020/11/17 14:18
 */
@Slf4j
@Component
@ServerEndpoint(value = "/websocket")
public class WebSocket {
    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    private String name;

    /**
     * 用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();

    /**
     * @Component的生成时间点早于@Autowired，所以依赖不到对象 private final SendMessageService sendMessageService;
     * <p>
     * public WebSocket(SendMessageService sendMessageService) {
     * this.sendMessageService = sendMessageService;
     * }
     */
    private static SendMessageService sendMessageService;

    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        WebSocket.sendMessageService = sendMessageService;
    }

    /**
     * 开启
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        //获取用户ip+port
        this.name = WebSocketUtil.getRemoteAddress(session).toString();
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(name, this);
        log.info("[WebSocket] 连接成功，当前连接人数为：={}", webSocketSet.size());
    }

    /**
     * 关闭
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this.name);
        log.info("[WebSocket] 退出成功，当前连接人数为：={}", webSocketSet.size());
    }

    /**
     * 交流
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("[WebSocket] 收到消息：{}", message);
        // 将消息先发给MQ，在监听到MQ推送消息后，再通过websocket进行群发
        if (!"".equals(message)) {
            sendMessageService.sendMessage(message);
        }
    }

    /**
     * 群发
     */
    public void groupSending(String message) {
        for (String name : webSocketSet.keySet()) {
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
    }

    /**
     * 指定发送
     * TODO mq运用的发布订阅模式，待改善
     *
     * @param name
     * @param message
     */
    public void appointSending(String name, String message) {
        try {
            webSocketSet.get(name).session.getBasicRemote().sendText(":这是我悄悄发给你的->" + message);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

}
