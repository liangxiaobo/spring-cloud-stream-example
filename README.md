项目的快速搭建参照官方 [Creating a Sample Application by Using Spring Initializr](https://docs.spring.io/spring-cloud-stream/docs/Elmhurst.RELEASE/reference/htmlsingle/#spring-cloud-stream-preface-creating-sample-application)

### RabbitMQ环境使用 
[RabbitMQ部署在DockerSwarm集群](https://www.jianshu.com/p/9243a9c020a3)

加入依赖
```xml
      <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-stream</artifactId>
        </dependency>
```
再选择Kafka或RabbitMQ
* Kafka
* RabbitMQ
比如我选择RabbitMQ，那么我项目的pom
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-stream</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
        </dependency>
```
### 消息处理
修改启动类
```java
@SpringBootApplication
@EnableBinding(Sink.class)
public class RabbitmqStreamExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqStreamExampleApplication.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void handle(Person person) {
        System.out.println("Received: " + person);
    }

    public static class Person {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String toString() {
            return this.name;
        }
    }
}
```

* @EnableBinding(Sink.class) 是绑定一个输入通道，Sink是提供的开箱即用的输入通道
* @StreamListener(Sink.INPUT) 监听输入进来的消息

Sink的源码
```java
public interface Sink {
    String INPUT = "input";

    @Input("input")
    SubscribableChannel input();
}
```
### 试试从RabbitMQ手动发消息
先启动项目，启动前配置一下rabbitmq连接
```xml
spring:
  application:
    name: rabbitmq-stream-example
  rabbitmq:
    host: 172.16.10.172
    port: 5672
    username: guest
    password: guest
server:
  port: 8080
```
**启动项目**
启动日志中有rabbitmq的连接及注册通道的信息
```bash
Initializing ExecutorService 'taskScheduler'
Registering MessageChannel input
Registering MessageChannel nullChannel
Registering MessageChannel errorChannel
Registering MessageHandler errorLogger
Channel 'rabbitmq-stream-example.input' has 1 subscriber(s).
Adding {logging-channel-adapter:_org.springframework.integration.errorLogger} as a subscriber to the 'errorChannel' channel
Channel 'rabbitmq-stream-example.errorChannel' has 1 subscriber(s).
started _org.springframework.integration.errorLogger
declaring queue for inbound: input.anonymous._RE-Zx6tQKWHDKGfc0NV9g, bound to: input
Attempting to connect to: [172.16.10.172:5672]
Created new connection: rabbitConnectionFactory#e72dba7:0/SimpleConnection@5f303ecd [delegate=amqp://guest@172.16.10.172:5672/, localPort= 55249]
Registering MessageChannel input.anonymous._RE-Zx6tQKWHDKGfc0NV9g.errors
Channel 'rabbitmq-stream-example.input.anonymous._RE-Zx6tQKWHDKGfc0NV9g.errors' has 1 subscriber(s).
Channel 'rabbitmq-stream-example.input.anonymous._RE-Zx6tQKWHDKGfc0NV9g.errors' has 2 subscriber(s).
started inbound.input.anonymous._RE-Zx6tQKWHDKGfc0NV9g
Started RabbitmqStreamExampleApplication in 2.376 seconds (JVM running for 3.764)
```
查看Rabbitmq的queue 
![input.anonymous._RE-Zx6tQKWHDKGfc0NV9g](https://upload-images.jianshu.io/upload_images/2151905-c97b6496e43cd817.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/760)
**手动发消息**
```json
{"name":"Sam Spade"}
```
![发送消息](https://upload-images.jianshu.io/upload_images/2151905-5f697e3b01fb5376.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/760)
查看控制台，已接收到消息
![控制台结果](https://upload-images.jianshu.io/upload_images/2151905-dcdbc58741e9d150.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/760)

### 应用模型
![SCSt-with-binder.png](https://upload-images.jianshu.io/upload_images/2151905-1c6439706de08862.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
应用程序能过Spring Cloud Stream注入的input和output与外界的连通是通过Binder实现，Spring Cloud Stream 提供了**Kafka**和**RabbitMQ**的Binder实现。

### 给消费者分组 ```spring.cloud.stream.bindings.<channelName>.group```
举个例子，假如只有一个消息生产者和一个消费者，消息能正常处理，在微服中可能一个消费者会有多个实例，一个消息会被多个实例处理，这样就出现了消息重复的问题，给消费者分组之后，一个消费者的多个实例中只会有一个实例处理消息

```xml
spring:
  application:
    name: rabbitmq-stream-example
  rabbitmq:
    host: 172.16.10.172
    port: 5672
    username: guest
    password: guest
  cloud:
    stream:
      bindings:
        input:
          destination: mqtestDefault # 指定了消息获取的目的地，对应于MQ就是 exchange，这里的exchange就是 mqTestDefault
          group: user-channel
        output:
          destination: mqtestDefault
          contentType: text/plain

server:
  port: 8080
```
**项目改造**
启动类
```java
@SpringBootApplication
@EnableBinding(Source.class)
public class RabbitmqStreamExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqStreamExampleApplication.class, args);
    }

}
```
创建一个消息监听  SinkMsgRecvicer
```java
@EnableBinding(Sink.class)
public class SinkMsgRecvicer {

    private static Logger logger = LoggerFactory.getLogger(SinkMsgRecvicer.class);

    @StreamListener(Sink.INPUT)
    public void msg(String value) {
        logger.info("Recvicer : {}", value);
    }
}
```
写一个测试的TestController
需要增加web依赖
```java
@RestController
public class TestController {

    @Autowired
    Source source;

    @RequestMapping("/send")
    public String send(String name) {
        source.output().send(MessageBuilder.withPayload("send to : " + name).build());

        return "发送成功 " + name;
    }
}
```
启动项目 访问 http://localhost:8080/send?name=liangwang
控制台会有输出
```
 Recvicer : send to : liangwang
```
![RabbitMq Exchanges](https://upload-images.jianshu.io/upload_images/2151905-b63317071d02daa8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/760)

待续。。。


> 引用
[源码 rabbitmq-stream-example](https://github.com/liangxiaobo/spring-cloud-stream-example/tree/master/rabbitmq-stream-example)<br/>
[官方文档 Elmhurst.RELEASE](https://docs.spring.io/spring-cloud-stream/docs/Elmhurst.RELEASE/reference/htmlsingle/#_spring_cloud_stream_core)
