package com.demo.servernoparam.mq;

import com.demo.servernoparam.config.RabbitmqConfig;
import com.demo.servernoparam.server.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author hong-2000
 * @version 1.0.0
 * @description 接受处理
 * @create 2020/11/20 10:17
 */
@Slf4j
@Component
public class ReceiveHandler {

    private static WebSocket webSocket;

    @Autowired
    public void setReceiveHandler(WebSocket webSocket) {
        ReceiveHandler.webSocket = webSocket;
    }

    private String mqMessage = "";

    /**
     * 监听 无参websocket服务 队列
     *
     * @param msg 消息 byte[]
     * @throws UnsupportedEncodingException
     */
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_WEBSOCKET_NO_PARAM})
    public void sendWebSocket(byte[] msg) throws UnsupportedEncodingException {
        this.mqMessage = new String(msg, "UTF-8");
        if(!"".equals(this.mqMessage)){
            pushMessage();
        }
        log.info("receive email message is:" + this.mqMessage);
        setMqMessageByNull();
    }
    /**
     * 分两种情况：
     * 1. 本服务器的客户端发来消息，存入MQ，然后取出服务器进行回应
     * 2. 从MQ获取到消息，主动推送消息给客户端
     */
    public void pushMessage(){
        webSocket.groupSending(this.mqMessage);
    }

    /**
     * 将mqMessage赋空值
     */
    public void setMqMessageByNull(){
        this.mqMessage = "";
    }
}
