# RabbitMQ

[processon笔记](https://www.processon.com/view/link/62d603317d9c0858545891ae)

### 工作模式

##### simple

[examples](https://github.com/liu149/rabbitmq-examples/tree/main/src/main/java/com/example/rabbitmq/simple)

![simple](https://github.com/liu149/rabbitmq-examples/blob/main/src/main/resources/static/simple.png?raw=true)



一个生产者，一个消费者的模式


##### work-queue

[examples](https://github.com/liu149/rabbitmq-examples/tree/main/src/main/java/com/example/rabbitmq/workQueue)

![workqueue](https://github.com/liu149/rabbitmq-examples/blob/main/src/main/resources/static/work-queue.png?raw=true)

一个生产者，多个消费者，一条消息只能被一个消费者消费



##### pub-sub

[examples](https://github.com/liu149/rabbitmq-examples/tree/main/src/main/java/com/example/rabbitmq/pubSub)

![pubSub](https://github.com/liu149/rabbitmq-examples/blob/main/src/main/resources/static/pub-sub.png?raw=true)

一个生产者，消息发送到交换机（type=fanout),交换机会将收到的消息广播给与之绑定的队列



##### routing

[examples](https://github.com/liu149/rabbitmq-examples/tree/main/src/main/java/com/example/rabbitmq/routing)

![routing](https://github.com/liu149/rabbitmq-examples/blob/main/src/main/resources/static/routing.png?raw=true)

生产者将消息发送给交换机(type=direct), 交换机根据路由键将消息分发给不同的队列，队列与交换机可以绑定多个路由键



##### topic

[examples](https://github.com/liu149/rabbitmq-examples/tree/main/src/main/java/com/example/rabbitmq/topic)

![topic](https://github.com/liu149/rabbitmq-examples/blob/main/src/main/resources/static/topic.png?raw=true)

topic是特殊的路由模式，路由键可以配置通配符（*代表一个单词，#代表多个单词）





### 高可用

##### 发送端确认

[examples](https://github.com/liu149/rabbitmq-examples/blob/main/src/main/java/com/example/rabbitmq/confirm/Publisher.java)



##### 消息持久化

[examples](https://github.com/liu149/rabbitmq-examples/tree/main/src/main/java/com/example/rabbitmq/durable)

交换机、队列、消息持久化



##### 接收端确认

[examples](https://github.com/liu149/rabbitmq-examples/blob/main/src/main/java/com/example/rabbitmq/confirm/Sub.java)

可以单个确认或者批量确认



### 高级特性

##### ttl

[examples](https://github.com/liu149/rabbitmq-examples/tree/main/src/main/java/com/example/rabbitmq/ttl)

可以将消息设置ttl，也可以将队列设置ttl（发送到队列的消息到期没消费会被丢弃）



##### 死信队列

[examples](https://github.com/liu149/rabbitmq-examples/tree/main/src/main/java/com/example/rabbitmq/dlx)

+ 消费者reject/Nack了，并且设置不入队(requeue=false)
+ 过期了
+ 超过队列最大长度



##### 延迟队列

[examples](https://github.com/liu149/rabbitmq-examples/tree/main/src/main/java/com/example/rabbitmq/dlx)

RabbitMQ没有实现延迟队列,但是可以通过ttl+DLX来实现















