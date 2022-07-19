package com.example.rabbitmq.durable;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Sender {

    private static final String EXCHANGE_NAME = "durable_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.8.100");
        factory.setPort(5672);
        factory.setVirtualHost("mall_vh");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 将交换机持久化
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
            String message = argv.length < 1 ? "info: Hello World!" :
                    String.join(" ", argv);
            // 消息持久化
            channel.basicPublish(EXCHANGE_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
