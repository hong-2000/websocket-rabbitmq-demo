package com.demo.producer.mq;

import com.demo.producer.config.RabbitmqConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hong-2000
 * @version 1.0.0
 * @program websocket
 * @description Topics模式生产者
 * @create 2020/11/20 10:38
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTopicsTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testSendByTopics() {
        int count = 10;
        for (int i = 0; i < count; i++) {
            String message = "sms email inform to user" + i;
            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, "inform.sms.email", message.getBytes());
            log.info("Send Message is:'" + message + "'");
        }
    }

}
