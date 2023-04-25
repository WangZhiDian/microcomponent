package com.meng.disruptor.consumer;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.meng.disruptor.bean.MessageModel;
import lombok.extern.slf4j.Slf4j;

/**
 * 消费者,用于处理ringbuffer中的数据，监听时间，两种处理的handler
 */
@Slf4j
public class MsgConsumer implements EventHandler<MessageModel>, WorkHandler<MessageModel> {

   private String name;
    public MsgConsumer(String name){
        this.name = name;
    }

    @Override
    public void onEvent(MessageModel msgEvent, long l, boolean b) throws Exception {
        try {
            //这里停止1000ms是为了确定消费消息是异步的
            log.info("handle->消费者"+name+"处理消息开始");
            if (msgEvent != null) {
                log.info("消费者"+name+"消费的信息是：{}",msgEvent.getMessage());
            }
        } catch (Exception e) {
            log.info("消费者"+name+"处理消息失败");
        }
        log.info("消费者"+name+"处理消息结束");
    }

    @Override
    public void onEvent(MessageModel messageModel) throws Exception {
        try {
            //这里停止1000ms是为了确定消费消息是异步的
            Thread.sleep(3000);
            if (messageModel != null) {
                log.info("worker->消费者"+name+"消费的信息是：{}",messageModel.getMessage());
            }
        } catch (Exception e) {
            log.info("消费者"+name+"处理消息失败");
        }
        log.info("消费者"+name+"处理消息结束");
    }
}
