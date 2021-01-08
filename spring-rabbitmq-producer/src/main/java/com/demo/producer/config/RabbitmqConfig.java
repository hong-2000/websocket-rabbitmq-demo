package com.demo.producer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hong-2000
 * @version 1.0.0
 * @program
 * @description Rabbitmq配置类
 * @create 2020/11/20 10:17
 */
@Configuration
public class RabbitmqConfig {
    /**
     * 队列名称
     */
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    /**
     * 交换机名称
     */
    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";
    /**
     * 路由key
     */
    public static final String ROUTING_KEY_EMAIL = "inform.#.email.#";
    public static final String ROUTING_KEY_SMS = "inform.#.sms.#";

    /**
     * 声明交换机
     *
     * @return
     */
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange exchangeTopicsInform() {
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(false).build();
    }

    /**
     * 声明QUEUE_INFORM_EMAIL队列
     *
     * @return
     */
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue queueInformEmail() {
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    /**
     * 声明QUEUE_INFORM_SMS队列
     *
     * @return
     */
    @Bean(QUEUE_INFORM_SMS)
    public Queue queueInformSms() {
        return new Queue(QUEUE_INFORM_SMS);
    }

    /**
     * ROUTING_KEY_EMAIL队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingQueueInformEmail(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue,
                                           @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_EMAIL).noargs();
    }

    /**
     * ROUTING_KEY_SMS队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingQueueInformSms(@Qualifier(QUEUE_INFORM_SMS) Queue queue,
                                         @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_SMS).noargs();
    }


}
