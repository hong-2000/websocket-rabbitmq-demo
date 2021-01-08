package com.dome.rabbitmq.f.header;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author hong-2000
 * @version 1.0.0
 * @program websocket
 * @description Header模式键值对-生产者
 * @create 2020/11/19 15:43
 */
@Slf4j
public class ProducerFiveHeader {
    /**
     * 队列名称
     */
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    /**
     * 交换机名称
     */
    private static final String EXCHANGE_HEADER_INFORM = "exchange_header_inform";

    public static void main(String[] args) {
        /**
         * 路由key，键值对
         */
        Map<String, Object> headersEmail = new Hashtable<String, Object>();
        headersEmail.put("inform_type", "email");
        Map<String, Object> headersSms = new Hashtable<String, Object>();
        headersSms.put("inform_type", "sms");

        Connection connection = null;
        Channel channel = null;
        try {
            //创建一个与MQ的连接
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            //rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务器
            factory.setVirtualHost("/");
            //创建一个连接
            connection = factory.newConnection();
            //创建与交换机的通道，每个通道代表一个会话
            channel = connection.createChannel();
            //声明交换机  (String exchange, BuiltinExchangeType type)
            /**
             * 参数明细
             * 1、交换机名称
             * 2、交换机类型，fanout、topic、direct、headers
             */
            channel.exchangeDeclare(EXCHANGE_HEADER_INFORM, BuiltinExchangeType.HEADERS);
            //声明队列  (String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String,Object> arguments)
            /**
             * 参数明细：
             * 1、队列名称
             * 2、是否持久化
             * 3、是否独占此队列
             * 4、队列不用是否自动删除
             * 5、参数
             */
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            //交换机和队列绑定  (String queue, String exchange, String routingKey)
            /**
             * 参数明细
             * 1、队列名称
             * 2、交换机名称
             * 3、路由key
             */
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_HEADER_INFORM,"", headersEmail);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_HEADER_INFORM, "", headersSms);
            // 定义消息数量，发送消息
            int count = 20;
            for (int i = 0; i < count; i++) {
                String message = "inform to user: " + i;
                //向交换机发送消息 String exchange, String routingKey, BasicProperties props,byte[] body
                /**
                 * 参数明细
                 * 1、交换机名称，不指令使用默认交换机名称 Default Exchange
                 * 2、routingKey（路由key），根据key名称将消息转发到具体的队列，这里填写队列名称表示消息将发到此队列
                 * 3、消息属性
                 * 4、消息内容
                 *
                 * @author 青红
                 */
                AMQP.BasicProperties.Builder propertiesEmail = new AMQP.BasicProperties.Builder();
                propertiesEmail.headers(headersEmail);
                AMQP.BasicProperties.Builder propertiesSms = new AMQP.BasicProperties.Builder();
                propertiesSms.headers(headersSms);

                if (i < 10) {
                    channel.basicPublish(EXCHANGE_HEADER_INFORM, "", propertiesEmail.build(), message.getBytes());
                } else {
                    channel.basicPublish(EXCHANGE_HEADER_INFORM, "", propertiesSms.build(), message.getBytes());
                }
                log.info("Send Message is:'" + message + "'");
            }
        } catch (IOException e) {
            log.error(e.toString());
        } catch (TimeoutException e) {
            log.error(e.toString());
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    log.error(e.toString());
                } catch (TimeoutException e) {
                    log.error(e.toString());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    log.error(e.toString());
                }
            }
        }
    }
}
