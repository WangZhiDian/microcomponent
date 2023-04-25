package com.meng.disruptor.producer;

import com.lmax.disruptor.RingBuffer;
import com.meng.disruptor.bean.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class MsgProducer {

    @Resource
    RingBuffer<MessageModel> ringBuffer;

    public void send(String message) {
        //获取下一个Event槽的下标
        long sequence = ringBuffer.next();
        try {
            //给Event填充数据
            MessageModel event = ringBuffer.get(sequence);
            event.setMessage(message);
            log.info("往消息队列中添加消息：{}", event);
        } catch (Exception e) {
            log.error("failed to add event to messageModelRingBuffer for : e = {},{}",e,e.getMessage());
        } finally {
            //发布Event，激活消费者去消费，将sequence传递给消费者
            //注意最后的publish方法必须放在finally中以确保必须得到调用；如果某个请求的sequence未被提交将会堵塞后续的发布操作或者其他的producer
            ringBuffer.publish(sequence);
        }
    }


}
