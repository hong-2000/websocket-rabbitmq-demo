package com.demo.serverparam.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hong-2000
 * @version 1.0.0
 * @description Rabbitmq配置类 发布订阅模式
 * @create 2020/11/20 10:17
 */
@Configuration
public class RabbitmqConfig {
    /**
     * 队列名称
     */
    public static final String QUEUE_INFORM_WEBSOCKET_NO_PARAM = "queue_inform_websocket_no_param";
    public static final String QUEUE_INFORM_WEBSOCKET_PARAM = "queue_inform_websocket_param";
    /**
     * 交换机名称
     */
    public static final String EXCHANGE_WEBSOCKET_INFORM = "exchange_websocket_inform";

    /**
     * 声明交换机
     *
     * @return
     */
    @Bean(EXCHANGE_WEBSOCKET_INFORM)
    public Exchange exchangeTopicsInform() {
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.fanoutExchange(EXCHANGE_WEBSOCKET_INFORM).durable(false).build();
    }

    /**
     * 声明QUEUE_INFORM_WEBSOCKET_NO_PARAM队列
     *
     * @return
     */
    @Bean(QUEUE_INFORM_WEBSOCKET_NO_PARAM)
    public Queue queueInformEmail() {
        return new Queue(QUEUE_INFORM_WEBSOCKET_NO_PARAM);
    }

    /**
     * 声明QUEUE_INFORM_WEBSOCKET_PARAM队列
     *
     * @return
     */
    @Bean(QUEUE_INFORM_WEBSOCKET_PARAM)
    public Queue queueInformSms() {
        return new Queue(QUEUE_INFORM_WEBSOCKET_PARAM);
    }

    /**
     * QUEUE_INFORM_WEBSOCKET_NO_PARAM队列绑定交换机，无需指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingQueueInformEmail(@Qualifier(QUEUE_INFORM_WEBSOCKET_NO_PARAM) Queue queue,
                                           @Qualifier(EXCHANGE_WEBSOCKET_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }

    /**
     * QUEUE_INFORM_WEBSOCKET_PARAM队列绑定交换机，无需指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingQueueInformSms(@Qualifier(QUEUE_INFORM_WEBSOCKET_PARAM) Queue queue,
                                         @Qualifier(EXCHANGE_WEBSOCKET_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }


}
