package com.example.rabbitmq.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class Producer {

    private final static String QUEUE_NAME = "ttl_queue";
    private final static String DLX_EXCHANGE = "dlx_exchange";


    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.8.100");
        factory.setPort(5672);
        factory.setVirtualHost("mall_vh");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 给队列设置ttl
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("x-message-ttl", 10000);
            args.put("x-dead-letter-exchange", DLX_EXCHANGE);
            args.put("x-dead-letter-routing-key", "dlx");

            // 声明死信交换机
            channel.exchangeDeclare(DLX_EXCHANGE, "direct");


            // 声明队列ttl时间和死信队列名称
            channel.queueDeclare(QUEUE_NAME, false, false, false, args);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
