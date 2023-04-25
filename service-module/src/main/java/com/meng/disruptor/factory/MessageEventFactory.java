package com.meng.disruptor.factory;

import com.lmax.disruptor.EventFactory;
import com.meng.disruptor.bean.MessageModel;

/**
 * 事件工厂,用户初始化和ringbuffer
 */
public class MessageEventFactory implements EventFactory<MessageModel> {

    @Override
    public MessageModel newInstance() {
        return new MessageModel();
    }

}
