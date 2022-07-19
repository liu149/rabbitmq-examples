package com.example.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingQueue;

// 发送放确认
public class Publisher {

    private final static String QUEUE_NAME = "hello";

    static ConcurrentNavigableMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

    static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.8.100");
        factory.setPort(5672);
        factory.setVirtualHost("mall_vh");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 将channel设置成确认模式
            channel.confirmSelect();
            // 异步监听
            channel.addConfirmListener((sequenceNumber, multiple) -> {
                // ACK
                if(multiple) {
                    ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(
                            sequenceNumber, true
                    );
                    confirmed.clear();
                }else {
                    outstandingConfirms.remove(sequenceNumber);
                }
            }, (sequenceNumber, multiple) -> {
                // NACK,这种情况就需要重发
                if(multiple) {
                    ConcurrentNavigableMap<Long, String> unConfirmed = outstandingConfirms.headMap(
                            sequenceNumber, true
                    );
                    unConfirmed.navigableKeySet();
                    for(Long key : unConfirmed.navigableKeySet()) {
                        queue.add(unConfirmed.get(key));
                    }
                    unConfirmed.clear();
                }else {
                    queue.add(outstandingConfirms.get(sequenceNumber));
                    outstandingConfirms.remove(sequenceNumber);
                }
            });
            // 确保不丢失消息需要将durable设置成true（queue持久化）
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 给队列发送一条消息
            queue.put("hello world");

            // 主线程保持从queue中取数据
            for(;;) {
                String message = queue.take();
                outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}
