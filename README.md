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











