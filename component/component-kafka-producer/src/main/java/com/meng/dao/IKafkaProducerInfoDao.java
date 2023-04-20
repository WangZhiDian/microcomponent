package com.meng.dao;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * kafka producer info dao
 *
 * @author : sunyuecheng
 */
public interface IKafkaProducerInfoDao {

    /**
     * send async message info
     *
     * @param topic :
     * @param key   :
     * @param msg   :
     */
    void sendAsyncMessageInfo(String topic, String key, String msg);

    /**
     * send async message info
     *
     * @param topic     :
     * @param partition :
     * @param key       :
     * @param msg       :
     */
    void sendAsyncMessageInfo(String topic, int partition, String key, String msg);

    /**
     * send sync message info
     *
     * @param topic   :
     * @param key     :
     * @param msg     :
     * @param timeout :
     * @throws InterruptedException :
     * @throws ExecutionException   :
     * @throws TimeoutException     :
     */
    void sendSyncMessageInfo(String topic, String key, String msg, long timeout)
            throws InterruptedException, ExecutionException, TimeoutException;

    /**
     * send sync message info
     *
     * @param topic     :
     * @param partition :
     * @param key       :
     * @param msg       :
     * @param timeout   :
     * @throws InterruptedException :
     * @throws ExecutionException   :
     * @throws TimeoutException     :
     */
    void sendSyncMessageInfo(String topic, int partition, String key, String msg, long timeout)
            throws InterruptedException, ExecutionException, TimeoutException;
}