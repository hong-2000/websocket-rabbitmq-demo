package com.demo.servernoparam.mq.impl;

import com.demo.servernoparam.config.RabbitmqConfig;
import com.demo.servernoparam.mq.SendMessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author hong-2000
 * @version 1.0.0
 * @description 发送消息到MQ
 * @create 2020/11/20 15:02
 */
@Service
public class SendMessageServiceImpl implements SendMessageService {
    /**
     * RabbitMQ，构造器注入
     */
    private final RabbitTemplate rabbitTemplate;

    public SendMessageServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMessage(String message){
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_WEBSOCKET_INFORM,"", message.getBytes());
    }

}
