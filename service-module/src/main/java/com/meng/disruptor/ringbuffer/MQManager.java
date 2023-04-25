package com.meng.disruptor.ringbuffer;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.meng.disruptor.bean.MessageModel;
import com.meng.disruptor.consumer.MsgConsumer;
import com.meng.disruptor.factory.MessageEventFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 定义ring buffer，使用特定的数据对象和处理时间进行绑定，并且启动该ring buffer
 */
@Configuration
public class MQManager {

    @Bean
    public RingBuffer<MessageModel> ringBuffer() {
        //定义用于事件处理的线程池， Disruptor通过java.util.concurrent.ExecutorSerivce提供的线程池来触发consumer的事件处理。
        //ExecutorService executor = Executors.newFixedThreadPool(3);   //这么写就认定只有3个消费者
        ThreadFactory executor = Executors.defaultThreadFactory();

        //指定事件工厂
        MessageEventFactory factory = new MessageEventFactory();

        //指定ringbuffer字节大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率
        //int bufferSize = 1024 * 256;
        int bufferSize = 8;

        //单生产者模式，当多个生产者时可以用ProducerType.MULTI
        Disruptor<MessageModel> disruptor = new Disruptor<>(factory, bufferSize, executor,
                ProducerType.SINGLE, new BlockingWaitStrategy());


        //定义消费者
        MsgConsumer msg1 = new MsgConsumer("1");
        MsgConsumer msg2 = new MsgConsumer("2");
        MsgConsumer msg3 = new MsgConsumer("3");
        MsgConsumer msg4 = new MsgConsumer("4");
        MsgConsumer msg5 = new MsgConsumer("5");


        //定义消费者执行模式（在这里一个消费者也就是一个线程，消费者执行模式也就是线程的执行模式）
//        disruptor.handleEventsWith(msg1, msg2, msg3, msg4);  //统一消费：一个消息会被所有消费者消费

//        disruptor.handleEventsWithWorkerPool(msg1, msg2);  //分组消费：一个消息只能被一个消费者消费，多消费者轮询处理

//        disruptor.handleEventsWith(msg1, msg3).then(msg2);   //顺序消费：1、3先并行处理，然后2再处理

        disruptor.handleEventsWith(msg1, msg3);  //多支线顺序消费：消费者1和消费者3一个支线，消费者2和消费者4一个支线，消费者3和消费者4消费完毕后，消费者5再进行消费
        disruptor.handleEventsWith(msg2, msg4);
        disruptor.after(msg3, msg4).handleEventsWith(msg5);


        // 启动disruptor线程
        //disruptor.start();

        //获取ringbuffer环，用于接取生产者生产的事件
        RingBuffer<MessageModel> ringBuffer = disruptor.getRingBuffer();

        return ringBuffer;
    }

    @Bean("ringBuffer2")
    public RingBuffer<MessageModel> ringBuffer2() {
        //定义用于事件处理的线程池， Disruptor通过java.util.concurrent.ExecutorSerivce提供的线程池来触发consumer的事件处理。
        ExecutorService executor = Executors.newFixedThreadPool(3);   //这么写就认定只有3个消费者
        //ThreadFactory executor = Executors.defaultThreadFactory();

        //指定事件工厂
        MessageEventFactory factory = new MessageEventFactory();

        //指定ringbuffer字节大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率
        //int bufferSize = 1024 * 256;
        int bufferSize = 8;

        //单生产者模式，当多个生产者时可以用ProducerType.MULTI
        Disruptor<MessageModel> disruptor = new Disruptor<>(factory, bufferSize, executor,
                ProducerType.SINGLE, new BlockingWaitStrategy());


        //定义消费者
        MsgConsumer msg1 = new MsgConsumer("1");
        MsgConsumer msg2 = new MsgConsumer("2");
        MsgConsumer msg3 = new MsgConsumer("3");


        //定义消费者执行模式（在这里一个消费者也就是一个线程，消费者执行模式也就是线程的执行模式）
//        disruptor.handleEventsWith(msg1, msg2, msg3, msg4);  //统一消费：一个消息会被所有消费者消费

        disruptor.handleEventsWithWorkerPool(msg1, msg2, msg3);  //分组消费：一个消息只能被一个消费者消费，多消费者轮询处理

//        disruptor.handleEventsWith(msg1, msg3).then(msg2);   //顺序消费：1、3先并行处理，然后2再处理


        // 启动disruptor线程
        //disruptor.start();

        //获取ringbuffer环，用于接取生产者生产的事件
        RingBuffer<MessageModel> ringBuffer = disruptor.getRingBuffer();

        return ringBuffer;
    }

}
