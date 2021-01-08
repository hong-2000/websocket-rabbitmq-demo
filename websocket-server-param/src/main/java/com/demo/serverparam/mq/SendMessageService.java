package com.demo.serverparam.mq;

/**
 * @author hong-2000
 * @version 1.0.0
 * @description 发送消息到MQ
 * @create 2020/11/20 15:05
 */
public interface SendMessageService {
    /**
     * 发送消息
     * @param message
     */
    void sendMessage(String message);
}
