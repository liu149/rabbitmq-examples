package com.example.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Sub {

    private static final String EXCHANGE_NAME = "durable_logs";
    private static final String QUEUE_NAME = "durable_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.8.100");
        factory.setPort(5672);
        factory.setVirtualHost("mall_vh");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 交换机持久化
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
        // 队列持久化
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            // 消息确认
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }
}
