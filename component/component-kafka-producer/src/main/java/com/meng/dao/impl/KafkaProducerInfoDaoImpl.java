package com.meng.dao.impl;

import com.meng.dao.IKafkaProducerInfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * kafka producer info dao impl
 * https://blog.csdn.net/qq_32641153/article/details/99132577
 * @author : sunyuecheng
 */
@Repository("KafkaProducerInfoDao")
public class KafkaProducerInfoDaoImpl implements IKafkaProducerInfoDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerInfoDaoImpl.class);

    @Autowired(required = false)
    private KafkaTemplate kafkaTemplate = null;

    public KafkaTemplate getKafkaTemplate() {
        return kafkaTemplate;
    }

    public void setKafkaTemplate(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendAsyncMessageInfo(String topic, String key, String msg) {
        if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(key) || msg == null) {
            LOGGER.error("Invalid param.");
            return;
        }
        ListenableFuture<SendResult<String, String>> listenableFuture
                = kafkaTemplate.send(topic, key, msg);

        SuccessCallback<SendResult<String, String>> successCallback
                = new SuccessCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOGGER.debug("Send message to kafka successfully.");
            }
        };

        FailureCallback failureCallback = new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error("Send message to kafka error, error,info({}).", ex.getMessage());
            }
        };
        listenableFuture.addCallback(successCallback, failureCallback);
    }

    @Override
    public void sendAsyncMessageInfo(String topic, int partition, String key, String msg) {
        if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(key) || msg == null) {
            LOGGER.error("Invalid param.");
            return;
        }
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, partition, key, msg);

        SuccessCallback<SendResult<String, String>> successCallback
                = new SuccessCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOGGER.debug("Send message to kafka successfully.");
            }
        };

        FailureCallback failureCallback = new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error("Send message to kafka error, error,info({}).", ex.getMessage());
            }
        };
        listenableFuture.addCallback(successCallback, failureCallback);
    }

    @Override
    public void sendSyncMessageInfo(String topic, String key, String msg, long timeout)
            throws InterruptedException, ExecutionException, TimeoutException {
        if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(key) || msg == null) {
            LOGGER.error("Invalid param.");
            return;
        }
        kafkaTemplate.send(topic, key, msg).get(timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public void sendSyncMessageInfo(String topic, int partition, String key, String msg, long timeout)
            throws InterruptedException, ExecutionException, TimeoutException {
        if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(key) || msg == null) {
            LOGGER.error("Invalid param.");
            return;
        }
        kafkaTemplate.send(topic, partition, key, msg).get(timeout, TimeUnit.MILLISECONDS);
    }

}
