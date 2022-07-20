package com.example.rabbitmq.ttl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class Producer {

    private final static String QUEUE_NAME = "ttl";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.8.100");
        factory.setPort(5672);
        factory.setVirtualHost("mall_vh");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 给队列设置ttl
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("x-message-ttl", 60000);

            channel.queueDeclare(QUEUE_NAME, false, false, false, args);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
