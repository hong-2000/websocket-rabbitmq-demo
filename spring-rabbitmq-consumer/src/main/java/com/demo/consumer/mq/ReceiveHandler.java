package com.demo.consumer.mq;

import com.demo.consumer.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author hong-2000
 * @version 1.0.0
 * @program
 * @description 接受处理
 * @create 2020/11/20 10:17
 */
@Slf4j
@Component
public class ReceiveHandler {

    /**
     * 监听email队列
     *
     * @param msg
     */
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void sendEmail(byte[] msg) throws UnsupportedEncodingException {
        String message = new String(msg, "UTF-8");
        log.info("receive email message is:" + message);
    }

    /**
     * 监听sms队列
     *
     * @param msg
     * @param message
     * @param channel
     */
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_SMS})
    public void sendSms(String msg, Message message, Channel channel) {
        log.info("receive sms message is:" + msg);
        //log.info("message:" + message + "\t channel:" + channel);
    }
}
