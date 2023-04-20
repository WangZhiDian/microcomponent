package com.meng.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringKafkaListener {

    //@KafkaListener(topics = "topic-demo")
/*    public void listener(ConsumerRecord<String, String> msg) {
        log.info(msg.toString());
        log.info(msg.topic());
    }*/

    @KafkaListener(topics = "topic-demo")
    public void listener(String msg) {
        log.info(msg);
    }

    @KafkaListener(topics = "topic-demo", groupId = "group1")
    public void kafkaListener(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        System.out.println("主题:" + topic);
        System.out.println("键key:" + key);
        System.out.println("消息:" + message);
    }

}
