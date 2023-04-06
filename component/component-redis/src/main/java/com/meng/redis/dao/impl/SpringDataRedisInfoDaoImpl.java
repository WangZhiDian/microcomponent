package com.meng.redis.dao.impl;

import com.meng.redis.dao.IRedisInfoDao;
import com.meng.redis.domain.ValueScoreInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring data redis info dao impl
 *
 * @author : sunyuecheng
 */
@Repository("SpringDataRedisInfoDao")
public class SpringDataRedisInfoDaoImpl implements IRedisInfoDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringDataRedisInfoDaoImpl.class);

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final int COLLECTION_INIT_SIZE = 16;
    private static final int BIT_LEN = 8;

    @Autowired(required = false)
    protected RedisTemplate redisTemplate = null;

    /**
     * save str info
     * @param key :
     * @param value :
     * @return boolean :
     */
    @Override
    public boolean saveStrInfo(String key, String value) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            LOGGER.error("Save str info error, error info(" + e.getMessage() + ").");
            return false;
        }
        return true;
    }

    /**
     * save str info ex
     * @param key :
     * @param value :
     * @param timeoutSeconds :
     * @return boolean :
     */
    @Override
    public boolean saveStrInfoEx(String key, String value, long timeoutSeconds) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForValue().set(key, value, timeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error("Save str info error, error info(" + e.getMessage() + ").");
            return false;
        }
        return true;
    }

    /**
     * save str info nx
     *
     * @param key            :
     * @param value          :
     * @return boolean :
     */
    @Override
    public boolean saveStrInfoNx(String key, String value) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        boolean ret = false;
        try {
            ret = redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
            LOGGER.error("Save str info error, error info(" + e.getMessage() + ").");
            return false;
        }
        return ret;
    }

    /**
     * get str info
     * @param key :
     * @return java.lang.String :
     */
    @Override
    public String getStrInfo(String key) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return null;
        }

        String value = null;
        try {
            value = (String) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            LOGGER.error("Get str info error, key(" + key + "),error info(" + e.getMessage() + ").");
        }
        return value;
    }

    /**
     * get set str info
     *
     * @param key :
     * @param value          :
     * @return String :
     */
    @Override
    public String getSetStrInfo(String key, String value) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return null;
        }

        String retValue = null;
        try {
            retValue = (String) redisTemplate.opsForValue().getAndSet(key, value);
        } catch (Exception e) {
            LOGGER.error("Get and set str info error, key(" + key + "),error info(" + e.getMessage() + ").");
        }
        return retValue;
    }

    /**
     * scan str info
     * @param pattern :
     * @param count :
     * @return List<String> :
     */
    @Override
    public List<String> scanStrInfo(String pattern, long count) {
        if (StringUtils.isEmpty(pattern) || count <= 0) {
            LOGGER.error("Invalid param.");
            return null;
        }

        List<String> valueList = null;
        try {
            valueList = (List<String>) redisTemplate.execute(new RedisCallback<List<String>>() {
                @Override
                public List<String> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    List<String> subValueList = new ArrayList<>();
                    ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(count).build();
                    Cursor<byte[]> cursor = redisConnection.scan(scanOptions);
                    while (cursor.hasNext()) {
                        try {
                            subValueList.add(new String(cursor.next(), DEFAULT_ENCODING));
                        } catch (Exception e) {
                            LOGGER.error("Scan str info error, error info(" + e.getMessage() + ").");
                            return null;
                        }
                    }
                    return subValueList;
                }
            });
            return valueList;
        } catch (Exception e) {
            LOGGER.error("Scan str info error, error info(" + e.getMessage() + ").");
        }

        return valueList;
    }

    /**
     * alter str byte info
     * @param key :
     * @param value :
     * @param offset :
     * @return boolean :
     */
    @Override
    public boolean alterStrByteInfo(final String key, final byte[] value, final long offset) {
        if (StringUtils.isEmpty(key) || value == null || offset < 0) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            String temp = (String) redisTemplate.opsForValue().get(key);
            if (StringUtils.isEmpty(temp)) {
                LOGGER.error("Invalid str value.");
                return false;
            }
            if (offset >= temp.length() || (value.length + offset) >= temp.length()) {
                LOGGER.error("Invalid param.");
                return false;
            }

            redisTemplate.execute(new RedisCallback<Void>() {
                @Override
                public Void doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    byte[] keyByte = null;
                    try {
                        keyByte = key.getBytes(DEFAULT_ENCODING);
                    } catch (Exception e) {
                        LOGGER.debug(e.getMessage());
                    }
                    if (keyByte != null) {
                        redisConnection.setRange(keyByte, value, offset);
                    }
                    return null;
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("Alter str byte info error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * get str byte info
     * @param key :
     * @param offset :
     * @param size :
     * @return byte[] :
     */
    @Override
    public byte[] getStrByteInfo(final String key, final long offset, final long size) {
        if (StringUtils.isEmpty(key) || offset < 0L || size <= 0L) {
            LOGGER.error("Invalid param.");
            return null;
        }

        try {
            String temp = (String) redisTemplate.opsForValue().get(key);
            if (StringUtils.isEmpty(temp)) {
                LOGGER.error("Invalid str value.");
                return null;
            }
            if (offset >= temp.length() || (size + offset) >= temp.length()) {
                LOGGER.error("Invalid param.");
                return null;
            }

            return (byte[]) redisTemplate.execute(new RedisCallback<byte[]>() {
                @Override
                public byte[] doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    byte[] keyByte = null;
                    try {
                        keyByte = key.getBytes(DEFAULT_ENCODING);
                    } catch (Exception e) {
                        LOGGER.debug(e.getMessage());
                    }
                    if (keyByte != null) {
                        return redisConnection.getRange(keyByte, offset, offset + size);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            LOGGER.error("Get str byte info error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * incr int info
     * @param key :
     * @return boolean :
     */
    @Override
    public boolean incrIntInfo(String key) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForValue().increment(key, 1L);
        } catch (Exception e) {
            LOGGER.error("Incr int info error, error info(" + e.getMessage() + ").");
            return false;
        }
        return true;
    }

    /**
     * decr int info
     * @param key :
     * @return boolean :
     */
    @Override
    public boolean decrIntInfo(String key) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForValue().increment(key, -1L);
        } catch (Exception e) {
            LOGGER.error("Decr int info error, error info(" + e.getMessage() + ").");
            return false;
        }
        return true;
    }

    /**
     * save bit map info
     * @param key :
     * @param value :
     * @return boolean :
     */
    @Override
    public boolean saveBitMapInfo(String key, byte[] value) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForValue().set(key.getBytes(DEFAULT_ENCODING), value);
            return true;
        } catch (Exception e) {
            LOGGER.error("Save bitmap info error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * get bit map info
     * @param key :
     * @return byte[] :
     */
    @Override
    public byte[] getBitMapInfo(String key) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return null;
        }

        byte[] value = null;
        try {
            value = (byte[]) redisTemplate.opsForValue().get(key.getBytes(DEFAULT_ENCODING));
        } catch (Exception e) {
            LOGGER.error("Get bitmap info error, key(" + key + "),error info(" + e.getMessage() + ").");
        }
        return value;
    }

    /**
     * set bit map bit info
     * @param key :
     * @param value :
     * @param offset :
     * @return boolean :
     */
    @Override
    public boolean setBitMapBitInfo(String key, boolean value, long offset) {
        if (StringUtils.isEmpty(key) || offset < 0L) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            byte[] temp = (byte[]) redisTemplate.opsForValue().get(key.getBytes(DEFAULT_ENCODING));
            if (temp == null) {
                LOGGER.error("Invalid bitmap value.");
                return false;
            }
            if (offset >= temp.length * BIT_LEN) {
                LOGGER.error("Invalid param.");
                return false;
            }
            redisTemplate.opsForValue().setBit(key, offset, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("Set bitmap bit info error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * get bit map bit info
     * @param key :
     * @param offset :
     * @return int :
     */
    @Override
    public int getBitMapBitInfo(String key, long offset) {
        if (StringUtils.isEmpty(key) || offset < 0L) {
            LOGGER.error("Invalid param.");
            return -1;
        }

        try {
            byte[] temp = (byte[]) redisTemplate.opsForValue().get(key.getBytes(DEFAULT_ENCODING));
            if (temp == null) {
                LOGGER.error("Invalid bitmap value.");
                return -1;
            }
            if (offset >= temp.length * BIT_LEN) {
                LOGGER.error("Invalid param.");
                return -1;
            }
            Boolean ret = redisTemplate.opsForValue().getBit(key.getBytes(DEFAULT_ENCODING), offset);
            return ret == null ? 0 : (ret ? 1 : 0);
        } catch (Exception e) {
            LOGGER.error("Get bitmap bit info error, error info(" + e.getMessage() + ").");
        }
        return -1;
    }

    /**
     * save str info to list
     * @param listName :
     * @param valueList :
     * @return boolean :
     */
    @Override
    public boolean saveStrInfoToList(final String listName, final List<String> valueList) {
        if (StringUtils.isEmpty(listName)) {
            LOGGER.error("Invalid param.");
            return false;
        }
        if (valueList.isEmpty()) {
            return true;
        }

        try {
            Long ret = redisTemplate.opsForList().rightPushAll(listName, valueList);
            return ret != null && (ret > 0L);
        } catch (Exception e) {
            LOGGER.error("Save str info to list error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * get str info list from list
     * @param listName :
     * @param beginIndex :
     * @param size :
     * @return java.util.List<java.lang.String> :
     */
    @Override
    public List<String> getStrInfoListFromList(String listName, long beginIndex, long size) {
        if (StringUtils.isEmpty(listName) || beginIndex < 0L) {
            LOGGER.error("Invalid param.");
            return null;
        }

        if (size == 0L) {
            return new ArrayList<String>();
        }

        try {
            if (size == -1L) {
                Long totalSize = redisTemplate.opsForList().size(listName);
                if (totalSize == null) {
                    LOGGER.error("Invalid param.");
                    return null;
                }
                size = totalSize - beginIndex;
            }

            List<String> strInfoList =
                    redisTemplate.opsForList().range(listName, beginIndex, beginIndex + size - 1L);
            return strInfoList;
        } catch (Exception e) {
            LOGGER.error("Get str info list from list error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * get str info from list
     * @param listName :
     * @param index :
     * @return java.lang.String :
     */
    @Override
    public String getStrInfoFromList(String listName, long index) {
        if (StringUtils.isEmpty(listName) || index < 0L) {
            LOGGER.error("Invalid param.");
            return null;
        }

        try {
            Long totalSize = redisTemplate.opsForList().size(listName);
            if (totalSize == null || index >= totalSize) {
                LOGGER.error("Invalid param.");
                return null;
            }
            return (String) redisTemplate.opsForList().index(listName, index);
        } catch (Exception e) {
            LOGGER.error("Get str info from list error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * trim str info from list
     * @param listName :
     * @param beginIndex :
     * @param endIndex :
     * @return boolean :
     */
    @Override
    public boolean trimStrInfoFromList(String listName, long beginIndex, long endIndex) {
        if (StringUtils.isEmpty(listName) || beginIndex <= -1L || endIndex < beginIndex) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForList().trim(listName, beginIndex, endIndex);
            return true;
        } catch (Exception e) {
            LOGGER.error("Delete str info from list error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * delete str info from list
     * @param listName :
     * @param vaule :
     * @return boolean :
     */
    @Override
    public boolean deleteStrInfoFromList(String listName, String vaule) {
        if (StringUtils.isEmpty(listName) || StringUtils.isEmpty(vaule)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForList().remove(listName, 0L, vaule);
            return true;
        } catch (Exception e) {
            LOGGER.error("Delete str info from list error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * get str info list size from list
     * @param listName :
     * @return long :
     */
    @Override
    public long getStrInfoListSizeFromList(String listName) {
        if (StringUtils.isEmpty(listName)) {
            LOGGER.error("Invalid param.");
            return 0L;
        }
        try {
            Long totalSize = redisTemplate.opsForList().size(listName);
            return totalSize == null ? 0L : totalSize;
        } catch (Exception e) {
            LOGGER.error("Get str info list size from list error, error info(" + e.getMessage() + ").");
        }
        return 0L;
    }

    /**
     * rpush str info to list
     * @param listName :
     * @param value :
     * @return boolean :
     */
    @Override
    public boolean rpushStrInfoToList(String listName, String value) {
        if (StringUtils.isEmpty(listName) || StringUtils.isEmpty(value)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            Long ret = redisTemplate.opsForList().rightPush(listName, value);
            return ret != null && (ret > 0L);
        } catch (Exception e) {
            LOGGER.error("Push str info to list error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * rpop str info from list
     * @param listName :
     * @return java.lang.String :
     */
    @Override
    public String rpopStrInfoFromList(String listName) {
        if (StringUtils.isEmpty(listName)) {
            LOGGER.error("Invalid param.");
            return null;
        }

        try {
            return (String) redisTemplate.opsForList().rightPop(listName);
        } catch (Exception e) {
            LOGGER.error("Pop str info from list error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * lpush str info to list
     * @param listName :
     * @param value :
     * @return boolean :
     */
    @Override
    public boolean lpushStrInfoToList(String listName, String value) {
        if (StringUtils.isEmpty(listName) || StringUtils.isEmpty(value)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            Long ret = redisTemplate.opsForList().leftPush(listName, value);
            return ret != null && (ret > 0L);
        } catch (Exception e) {
            LOGGER.error("Push str info to list error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * lpop str info from list
     * @param listName :
     * @return java.lang.String :
     */
    @Override
    public String lpopStrInfoFromList(String listName) {
        if (StringUtils.isEmpty(listName)) {
            LOGGER.error("Invalid param.");
            return null;
        }

        try {
            return (String) redisTemplate.opsForList().leftPop(listName);
        } catch (Exception e) {
            LOGGER.error("Pop str info from list error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * save obj info
     * @param key :
     * @param obj :
     * @return boolean :
     */
    @Override
    public <T> boolean saveObjInfo(String key, T obj) {
        if (StringUtils.isEmpty(key) || obj == null) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            Map data = objectToHashMap(obj);
            redisTemplate.opsForHash().putAll(key, data);
            return true;
        } catch (Exception e) {
            LOGGER.error("Save obj info error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * get obj info
     * @param key :
     * @param cls :
     * @return T :
     */
    @Override
    public <T> T getObjInfo(String key, Class<T> cls) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return null;
        }

        T retObj = null;
        try {
            Map<String, String> data = redisTemplate.opsForHash().entries(key);
            if (data != null) {
                retObj = mapToObject(data, cls);
            }
        } catch (Exception e) {
            LOGGER.error("Get obj info error, error info(" + e.getMessage() + ").");
        }
        return retObj;
    }

    /**
     * get obj item info
     * @param key :
     * @param itemName :
     * @return java.lang.String :
     */
    @Override
    public String getObjItemInfo(String key, String itemName) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(itemName)) {
            LOGGER.error("Invalid param.");
            return null;
        }

        String retObj = null;
        try {
            retObj = (String) redisTemplate.opsForHash().get(key, itemName);

            return retObj;
        } catch (Exception e) {
            LOGGER.error("Get obj item info error, error info(" + e.getMessage() + ").");
        }
        return retObj;
    }

    /**
     * update obj item info
     * @param key :
     * @param itemName :
     * @param item :
     * @return boolean :
     */
    @Override
    public boolean updateObjItemInfo(String key, String itemName, String item) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(itemName)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForHash().put(key, itemName, item);
            return true;
        } catch (Exception e) {
            LOGGER.error("Update obj item info error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * save map info
     * @param key :
     * @param map :
     * @return boolean :
     */
    @Override
    public boolean saveMapInfo(String key, Map<String, String> map) {
        if (StringUtils.isEmpty(key) || map == null) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            LOGGER.error("Save map info error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * get map info
     * @param key :
     * @return Map<String ,   String> :
     */
    @Override
    public Map<String, String> getMapInfo(String key) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return null;
        }

        try {
            Map<String, String> data = redisTemplate.opsForHash().entries(key);
            return data;
        } catch (Exception e) {
            LOGGER.error("Get map info error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * get map item info
     * @param key :
     * @param itemName :
     * @return java.lang.String :
     */
    @Override
    public String getMapItemInfo(String key, String itemName) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(itemName)) {
            LOGGER.error("Invalid param, key({}), itemName({}).", key, itemName);
            return null;
        }

        String retObj = null;
        try {
            Object obj = redisTemplate.opsForHash().get(key, itemName);
            if (obj != null) {
                retObj = (String) obj;
            }

            return retObj;
        } catch (Exception e) {
            LOGGER.error("Get map item info error, key(" + key + "), item name(" + itemName + "), "
                    + "error info(" + e.getMessage() + ").");
        }
        return retObj;
    }

    /**
     * update map item info
     * @param key :
     * @param itemName :
     * @param item :
     * @return boolean :
     */
    @Override
    public boolean updateMapItemInfo(String key, String itemName, String item) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(itemName)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForHash().put(key, itemName, item);
            return true;
        } catch (Exception e) {
            LOGGER.error("Update obj item info error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * delete map item info
     * @param key :
     * @param itemName :
     * @return boolean :
     */
    @Override
    public boolean deleteMapItemInfo(String key, String itemName) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(itemName)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForHash().delete(key, itemName);
            return true;
        } catch (Exception e) {
            LOGGER.error("Delete obj item info error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * get map item info size
     * @param key :
     * @return long :
     */
    @Override
    public long getMapItemInfoSize(String key) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return 0L;
        }

        try {
            return redisTemplate.opsForHash().size(key);
        } catch (Exception e) {
            LOGGER.error("Delete obj item info error, error info(" + e.getMessage() + ").");
        }
        return 0L;
    }

    /**
     * scan map item info
     *
     * @param key     :
     * @param pattern :
     * @param count   :
     * @return List<String> :
     */
    @Override
    public List<String> scanMapItemInfo(String key, String pattern, long count) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(pattern)) {
            LOGGER.error("Invalid param.");
            return null;
        }

        List<String> valueList = new ArrayList<>();

        ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(count).build();
        try (Cursor<Map.Entry<Object, Object>> curosr = redisTemplate.opsForHash().scan(key, scanOptions)) {
            while (curosr.hasNext()) {
                Map.Entry<Object, Object> entry = curosr.next();
                valueList.add(entry.getValue().toString());
            }
            return valueList;
        } catch (Exception e) {
            LOGGER.error("Scan map item info error, error info({}).", e.getMessage());
        }
        return valueList;
    }

    /**
     * scan map item info
     *
     * @param key     :
     * @param pattern :
     * @param count   :
     * @return Map<String ,   String>:
     */
    @Override
    public Map<String, String> scanMapInfo(String key, String pattern, long count) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(pattern)) {
            LOGGER.error("Invalid param.");
            return null;
        }

        Map<String, String> keyValueList = new HashMap<>();

        ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(count).build();
        try (Cursor<Map.Entry<Object, Object>> curosr = redisTemplate.opsForHash().scan(key, scanOptions)) {
            while (curosr.hasNext()) {
                Map.Entry<Object, Object> entry = curosr.next();
                keyValueList.put(entry.getKey().toString(), entry.getValue().toString());
            }
            return keyValueList;
        } catch (Exception e) {
            LOGGER.error("Scan map item info error, error info({}).", e.getMessage());
        }
        return keyValueList;
    }

    /**
     * save str info to set
     * @param setName :
     * @param value :
     * @return boolean :
     */
    @Override
    public boolean saveStrInfoToSet(String setName, String value) {
        if (StringUtils.isEmpty(setName) || StringUtils.isEmpty(value)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForSet().add(setName, value);
        } catch (Exception e) {
            LOGGER.error("Save str info to set error, error info(" + e.getMessage() + ").");
            return false;
        }
        return true;
    }

    /**
     * pop str info from set
     * @param setName :
     * @return java.lang.String :
     */
    @Override
    public String popStrInfoFromSet(String setName) {
        if (StringUtils.isEmpty(setName)) {
            LOGGER.error("Invalid param.");
            return null;
        }

        try {
            return (String) redisTemplate.opsForSet().pop(setName);
        } catch (Exception e) {
            LOGGER.error("Pop str info from set error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * delete str info from set
     * @param setName :
     * @param value :
     * @return boolean :
     */
    @Override
    public boolean deleteStrInfoFromSet(String setName, String value) {
        if (StringUtils.isEmpty(setName) || StringUtils.isEmpty(value)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForSet().remove(setName, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("Delete str info from set error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * check exist str info from set
     * @param setName :
     * @param value :
     * @return boolean :
     */
    @Override
    public boolean checkExistStrInfoFromSet(String setName, String value) {
        if (StringUtils.isEmpty(setName) || StringUtils.isEmpty(value)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            Boolean ret = redisTemplate.opsForSet().isMember(setName, value);
            return ret == null ? false : ret;
        } catch (Exception e) {
            LOGGER.error("Check exist str info from set error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * get str info set size from set
     * @param setName :
     * @return long :
     */
    @Override
    public long getStrInfoSetSizeFromSet(String setName) {
        if (StringUtils.isEmpty(setName)) {
            LOGGER.error("Invalid param.");
            return 0L;
        }

        try {
            Long ret = redisTemplate.opsForSet().size(setName);
            return ret == null ? 0L : ret;
        } catch (Exception e) {
            LOGGER.error("Get set size error, error info(" + e.getMessage() + ").");
        }
        return 0L;
    }

    /**
     * scan str info from set
     * @param setName :
     * @param pattern :
     * @param count :
     * @return java.util.List<java.lang.String> :
     */
    @Override
    public List<String> scanStrInfoFromSet(String setName, String pattern, long count) {
        if (StringUtils.isEmpty(setName) || StringUtils.isEmpty(pattern)) {
            LOGGER.error("Invalid param.");
            return null;
        }

        ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(count).build();
        try (Cursor<String> curosr =
                     redisTemplate.opsForSet().scan(setName, scanOptions)) {

            List<String> valueList = new ArrayList<>();
            while (curosr.hasNext()) {
                valueList.add(curosr.next());
            }
            return valueList;
        } catch (Exception e) {
            LOGGER.error("Scan str info from set error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * save str info to sort set
     * @param setName :
     * @param value :
     * @param score :
     * @return boolean :
     */
    @Override
    public boolean saveStrInfoToSortSet(String setName, String value, double score) {
        if (StringUtils.isEmpty(setName) || StringUtils.isEmpty(value)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForZSet().add(setName, value, score);
        } catch (Exception e) {
            LOGGER.error("Save str info to sort set error, error info(" + e.getMessage() + ").");
            return false;
        }
        return true;
    }

    /**
     * save str info list to sort set
     * @param setName :
     * @param valueList :
     * @return boolean :
     */
    @Override
    public boolean saveStrInfoListToSortSet(String setName, Set<DefaultTypedTuple<String>> valueList) {
        if (StringUtils.isEmpty(setName) || valueList == null || valueList.isEmpty()) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForZSet().add(setName, valueList);
        } catch (Exception e) {
            LOGGER.error("Save str info to sort set error, error info(" + e.getMessage() + ").");
            return false;
        }
        return true;
    }

    /**
     * get score by str from sort set
     * @param stName :
     * @param value :
     * @return Double :
     */
    @Override
    public Double getScoreByStrFromSortSet(String stName, String value) {
        if (StringUtils.isEmpty(stName) || StringUtils.isEmpty(value)) {
            LOGGER.error("Invalid param.");
            return null;
        }
        try {
            return redisTemplate.opsForZSet().score(stName, value);
        } catch (Exception e) {
            LOGGER.error("Get sort set value score error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * incr str score info
     * @param setName :
     * @param value :
     * @return boolean :
     */
    @Override
    public boolean incrStrScoreInfo(String setName, String value) {
        if (StringUtils.isEmpty(setName) || StringUtils.isEmpty(value)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForZSet().incrementScore(setName, value, 1);
        } catch (Exception e) {
            LOGGER.error("Incr str score info error, error info(" + e.getMessage() + ").");
            return false;
        }
        return true;
    }

    /**
     * decr str score info
     * @param setName :
     * @param value :
     * @return boolean :
     */
    @Override
    public boolean decrStrScoreInfo(String setName, String value) {
        if (StringUtils.isEmpty(setName) || StringUtils.isEmpty(value)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForZSet().incrementScore(setName, value, -1L);
        } catch (Exception e) {
            LOGGER.error("Decr str score info error, error info(" + e.getMessage() + ").");
            return false;
        }
        return true;
    }

    /**
     * get str info list from sort set
     * @param setName :
     * @param beginIndex :
     * @param size :
     * @param desc :
     * @return java.util.Set<java.lang.String> :
     */
    @Override
    public Set<String> getStrInfoListFromSortSet(String setName, long beginIndex, long size, boolean desc) {
        if (StringUtils.isEmpty(setName) || beginIndex < 0L) {
            LOGGER.error("Invalid param.");
            return null;
        }

        if (size == 0) {
            return new HashSet<>();
        }

        try {
            if (size == -1) {
                Long totalSize = redisTemplate.opsForZSet().size(setName);
                if (totalSize == null) {
                    LOGGER.error("Invalid param.");
                    return null;
                }
                size = totalSize - beginIndex;
            }

            Set<String> strInfoList = null;
            if (desc) {
                strInfoList = redisTemplate.opsForZSet().range(setName, beginIndex, beginIndex + size - 1L);
            } else {
                strInfoList = redisTemplate.opsForZSet().reverseRange(setName, beginIndex, beginIndex + size - 1L);
            }
            return strInfoList;
        } catch (Exception e) {
            LOGGER.error("Get str info list from sort set error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * get str info list by score from sort set
     * @param setName :
     * @param min :
     * @param max :
     * @param beginIndex :
     * @param size :
     * @param desc :
     * @return java.util.Set<java.lang.String> :
     */
    @Override
    public Set<String> getStrInfoListByScoreFromSortSet(String setName,
                                                        double min, double max,
                                                        long beginIndex, long size, boolean desc) {
        if (StringUtils.isEmpty(setName) || beginIndex < 0L) {
            LOGGER.error("Invalid param.");
            return null;
        }

        if (size == 0) {
            return new HashSet<>();
        }
        try {
            if (size == -1) {
                Long totalSize = redisTemplate.opsForZSet().size(setName);
                if (totalSize == null) {
                    LOGGER.error("Invalid param.");
                    return null;
                }
                size = totalSize - beginIndex;
            }
            Set<String> strInfoList = null;
            if (desc) {
                strInfoList = redisTemplate.opsForZSet().rangeByScore(setName, min, max, beginIndex, size);
            } else {
                strInfoList = redisTemplate.opsForZSet().reverseRangeByScore(setName, min, max, beginIndex, size);
            }
            return strInfoList;
        } catch (Exception e) {
            LOGGER.error("Get str info list from sort set error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * set< value score info> get value score info list by score from sort set
     *
     * @param setName    :
     * @param min        :
     * @param max        :
     * @param beginIndex :
     * @param size       :
     * @param desc       :
     * @return :
     */
    @Override
    public Set<ValueScoreInfo> getValueScoreInfoListByScoreFromSortSet(String setName, double min, double max,
                                                                       long beginIndex, long size, boolean desc) {
        if (StringUtils.isEmpty(setName) || beginIndex < 0) {
            LOGGER.error("Invalid param.");
            return null;
        }

        if (size == 0) {
            return new HashSet<>();
        }

        try {
            if (size == -1) {
                long totalSize = redisTemplate.opsForZSet().size(setName);
                size = totalSize - beginIndex;
            }

            Set<ZSetOperations.TypedTuple> typedTupleSet = null;
            Set<ValueScoreInfo> valueScoreInfoSet = null;
            if (desc) {
                typedTupleSet = redisTemplate.opsForZSet().rangeByScoreWithScores(setName, min, max, beginIndex, size);
                valueScoreInfoSet = new TreeSet<>(new Comparator<ValueScoreInfo>() {
                    @Override
                    public int compare(ValueScoreInfo value1, ValueScoreInfo value2) {
                        if (!value1.getScore().equals(value2.getScore())) {
                            return (int) (value1.getScore() - value2.getScore());
                        }
                        return value1.getValue().compareTo(value2.getValue());
                    }
                });
            } else {
                typedTupleSet = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(setName, min, max, beginIndex,
                        size);
                valueScoreInfoSet = new TreeSet<>(new Comparator<ValueScoreInfo>() {
                    @Override
                    public int compare(ValueScoreInfo value1, ValueScoreInfo value2) {
                        if (!value1.getScore().equals(value2.getScore())) {
                            return (int) (value2.getScore() - value1.getScore());
                        }
                        return value1.getValue().compareTo(value2.getValue());
                    }
                });
            }


            for (ZSetOperations.TypedTuple typedTuple : typedTupleSet) {
                valueScoreInfoSet.add(new ValueScoreInfo(typedTuple.getValue().toString(),
                        typedTuple.getScore().longValue()));
            }
            return valueScoreInfoSet;

        } catch (Exception e) {
            LOGGER.error("Get value score info list from sort set error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * get extreme score from sort set
     *
     * @param setName :
     * @param desc    :
     * @return long :
     */
    @Override
    public long getExtremeScoreFromSortSet(String setName, boolean desc) {
        if (StringUtils.isEmpty(setName)) {
            LOGGER.error("Invalid param.");
            return 0L;
        }
        long value = 0L;

        try {
            Set<String> valueStrSet = null;
            if (desc) {
                valueStrSet = redisTemplate.opsForZSet().range(setName, -1, -1);
            } else {
                valueStrSet = redisTemplate.opsForZSet().range(setName, 0, 0);
            }
            if (valueStrSet != null && valueStrSet.size() == 1) {
                for (String valueStr : valueStrSet) {
                    double valueDouble = redisTemplate.opsForZSet().score(setName, valueStr);
                    value = new Double(valueDouble).longValue();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Get extrama score from sort set error, error info(" + e.getMessage() + ").");
        }
        return value;
    }

    /**
     * delete str info from sort set
     * @param setName :
     * @param value :
     * @return boolean :
     */
    @Override
    public boolean deleteStrInfoFromSortSet(String setName, String value) {
        if (StringUtils.isEmpty(setName) || StringUtils.isEmpty(value)) {
            LOGGER.error("Invalid param.");
            return false;
        }
        try {
            redisTemplate.opsForZSet().remove(setName, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("Delete str info from sort set error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * delete str info by score from sort set
     * @param setName :
     * @param min :
     * @param max :
     * @return boolean :
     */
    @Override
    public boolean deleteStrInfoByScoreFromSortSet(String setName, double min, double max) {
        if (StringUtils.isEmpty(setName)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForZSet().removeRangeByScore(setName, min, max);
            return true;
        } catch (Exception e) {
            LOGGER.error("Delete str info by score from sort set error, error info("
                    + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * boolean delete str info from sort set
     * @param setName :
     * @param beginIndex :
     * @param endIndex :
     * @return  :
     */
    @Override
    public boolean deleteStrInfoFromSortSet(String setName, long beginIndex, long endIndex) {
        if (StringUtils.isEmpty(setName)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.opsForZSet().removeRange(setName, beginIndex, endIndex);
            return true;
        } catch (Exception e) {
            LOGGER.error("Delete str info from sort set error, error info("
                    + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * get str info set size by score from sort set
     * @param setName :
     * @param min :
     * @param max :
     * @return long :
     */
    @Override
    public long getStrInfoSetSizeByScoreFromSortSet(String setName, double min, double max) {
        if (StringUtils.isEmpty(setName)) {
            LOGGER.error("Invalid param.");
            return 0L;
        }

        try {
            Long count = redisTemplate.opsForZSet().count(setName, min, max);
            return count == null ? 0L : count;
        } catch (Exception e) {
            LOGGER.error("Get str info size by score from sort set error, error info("
                    + e.getMessage() + ").");
        }
        return 0L;
    }

    /**
     * get str info size from sort set
     * @param setName :
     * @return long :
     */
    @Override
    public long getStrInfoSizeFromSortSet(String setName) {
        if (StringUtils.isEmpty(setName)) {
            LOGGER.error("Invalid param.");
            return 0L;
        }

        try {
            Long ret = redisTemplate.opsForZSet().size(setName);
            return ret == null ? 0L : ret;
        } catch (Exception e) {
            LOGGER.error("Get sort set size error, error info(" + e.getMessage() + ").");
        }
        return 0L;
    }

    /**
     * scan str info from sort set
     * @param setName :
     * @param pattern :
     * @param count :
     * @return java.util.List<java.lang.String> :
     */
    @Override
    public List<String> scanStrInfoFromSortSet(String setName, String pattern, long count) {
        if (StringUtils.isEmpty(setName) || StringUtils.isEmpty(pattern)) {
            LOGGER.error("Invalid param.");
            return null;
        }
        ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(count).build();
        try (Cursor<DefaultTypedTuple<String>> curosr =
                     redisTemplate.opsForZSet().scan(setName, scanOptions)) {

            List<String> valueList = new ArrayList<>();
            while (curosr.hasNext()) {
                valueList.add(curosr.next().getValue());
            }
            return valueList;
        } catch (Exception e) {
            LOGGER.error("Scan str info from sort set error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * scan value score info from sort set
     * @param setName :
     * @param pattern :
     * @param count :
     * @return Set<ValueScoreInfo> :
     */
    @Override
    public Set<ValueScoreInfo> scanValueScoreInfoFromSortSet(String setName, String pattern, long count) {
        if (StringUtils.isEmpty(setName) || StringUtils.isEmpty(pattern)) {
            LOGGER.error("Invalid param.");
            return null;
        }
        ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(count).build();
        try (Cursor<DefaultTypedTuple<String>> curosr =
                     redisTemplate.opsForZSet().scan(setName, scanOptions)) {

            Set<ValueScoreInfo> valueScoreInfoSet = new HashSet<>(COLLECTION_INIT_SIZE);

            while (curosr.hasNext()) {
                DefaultTypedTuple<String> curosrTmp = curosr.next();
                ValueScoreInfo valueScoreInfo = new ValueScoreInfo(curosrTmp.getValue(),
                        Objects.requireNonNull(curosrTmp.getScore()).longValue());
                valueScoreInfoSet.add(valueScoreInfo);
            }
            return valueScoreInfoSet;
        } catch (Exception e) {
            LOGGER.error("Scan str info from sort set error, error info({}).", e.getMessage());
        }

        return null;
    }

    /**
     * get keys
     * @param pattern :
     * @return java.util.Set<java.lang.String> :
     */
    @Override
    public Set<String> getKeys(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            LOGGER.error("Invalid param.");
            return null;
        }

        try {
            Set<String> keySet = redisTemplate.keys(pattern);
            return keySet;
        } catch (Exception e) {
            LOGGER.error("Get keys error, error info(" + e.getMessage() + ").");
        }
        return null;
    }

    /**
     * delete key
     * @param key :
     * @return boolean :
     */
    @Override
    public boolean deleteKey(String key) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            LOGGER.error("Delete key error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * expire key
     * @param key :
     * @param timeoutSeconds :
     * @return boolean :
     */
    @Override
    public boolean expireKey(String key, long timeoutSeconds) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.expire(key, timeoutSeconds, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            LOGGER.error("Expire key error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * expire key at
     * @param key :
     * @param expiredTime :
     * @return boolean :
     */
    @Override
    public boolean expireKeyAt(String key, long expiredTime) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        try {
            redisTemplate.expireAt(key, new Date(expiredTime));
            return true;
        } catch (Exception e) {
            LOGGER.error("Expire key error, error info(" + e.getMessage() + ").");
        }
        return false;
    }

    /**
     * get expire
     * @param key :
     * @return long :
     */
    @Override
    public long getExpire(String key) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return -1L;
        }

        try {
            Long ret = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            return ret == null ? -1L : ret;
        } catch (Exception e) {
            LOGGER.error("Expire key error, error info(" + e.getMessage() + ").");
        }
        return -1L;
    }

    /**
     * exists
     * @param key :
     * @return boolean :
     */
    @Override
    public boolean exists(String key) {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        boolean ret = false;
        try {
            Boolean tmp = redisTemplate.hasKey(key);
            if (tmp != null) {
                ret = tmp.booleanValue();
            }
        } catch (Exception e) {
            LOGGER.error("Exist key error, error info(" + e.getMessage() + ").");
        }
        return ret;
    }

    /**
     * rename
     *
     * @param oldKey :
     * @param newKey :
     * @return boolean :
     */
    @Override
    public boolean rename(String oldKey, String newKey) {
        if (StringUtils.isEmpty(oldKey) || StringUtils.isEmpty(newKey)) {
            LOGGER.error("Invalid param.");
            return false;
        }

        boolean ret = false;
        try {
            Boolean tmp = redisTemplate.renameIfAbsent(oldKey, newKey);
            if (tmp != null) {
                ret = tmp.booleanValue();
            }
        } catch (Exception e) {
            LOGGER.error("Rename key error, error info(" + e.getMessage() + ").");
        }
        return ret;
    }

    /**
     * get current time
     *
     * @return : long
     */
    public long getCurrentTime() {
        Long ret = (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.time();
            }
        });
        return ret == null ? 0L : ret;
    }

    /**
     * object to map
     *
     * @param obj :
     * @param <T> :
     * @return Map<String ,   String> :
     * @throws Exception :
     */
    public <T> Map<String, String> objectToHashMap(T obj) throws Exception {

        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        Map<String, String> hashMap = new HashMap<String, String>(COLLECTION_INIT_SIZE);

        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldName != null) {
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
                Method getMethod = pd.getReadMethod();

                Object itemObj = getMethod.invoke(obj);

                Class type = field.getType();

                String key = fieldName;
                String value = null;
                if (Integer.class.equals(type) || int.class.equals(type)) {
                    value = String.valueOf(itemObj);
                } else if (Long.class.equals(type) || long.class.equals(type)) {
                    value = String.valueOf(itemObj);
                } else if (Boolean.class.equals(type) || boolean.class.equals(type)) {
                    value = String.valueOf(itemObj);
                } else if (Date.class.equals(type)) {
                    value = String.valueOf(((Date) itemObj).getTime());
                } else if (Short.class.equals(type) || short.class.equals(type)) {
                    value = String.valueOf(itemObj);
                } else if (Float.class.equals(type) || float.class.equals(type)) {
                    value = String.valueOf(itemObj);
                } else if (Double.class.equals(type) || double.class.equals(type)) {
                    value = String.valueOf(itemObj);
                } else if (String.class.equals(type)) {
                    value = (String) itemObj;
                } else {
                    throw new Exception("Unsupport value type,field name("
                            + fieldName + "),type(" + type.getName() + ").");
                }

                if (value != null) {
                    hashMap.put(key, value);
                }
            }
        }
        return hashMap;
    }

    /**
     * map to object
     *
     * @param data :
     * @param <T>  :
     * @param cls  :
     * @return T :
     * @throws Exception :
     */
    public <T> T mapToObject(Map<String, String> data, Class<T> cls) throws Exception {

        Field[] fields = cls.getDeclaredFields();
        T retObj = cls.newInstance();
        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldValue = data.get(fieldName);
            if (fieldName != null && fieldValue != null) {
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, cls);
                Method setMethod = pd.getWriteMethod();

                Class type = field.getType();
                if (Integer.class.equals(type) || int.class.equals(type)) {
                    Integer integerVal = Integer.valueOf(fieldValue);
                    setMethod.invoke(retObj, integerVal);
                } else if (Long.class.equals(type) || long.class.equals(type)) {
                    Long longVal = Long.valueOf(fieldValue);
                    setMethod.invoke(retObj, longVal);
                } else if (Boolean.class.equals(type) || boolean.class.equals(type)) {
                    Boolean boolVal = Boolean.getBoolean(fieldValue);
                    setMethod.invoke(retObj, boolVal);
                } else if (Date.class.equals(type)) {
                    Long longVal = Long.valueOf(fieldValue);
                    Date date = new Date(longVal);
                    setMethod.invoke(retObj, date);
                } else if (Short.class.equals(type) || short.class.equals(type)) {
                    setMethod.invoke(retObj, Short.valueOf(fieldValue));
                } else if (Float.class.equals(type) || float.class.equals(type)) {
                    setMethod.invoke(retObj, Float.valueOf(fieldValue));
                } else if (Double.class.equals(type) || double.class.equals(type)) {
                    setMethod.invoke(retObj, Double.valueOf(fieldValue));
                } else if (String.class.equals(type)) {
                    setMethod.invoke(retObj, fieldValue);
                } else {
                    throw new Exception("Unsupport value type,field name(" + fieldName
                            + "),type(" + type.getName() + ").");
                }

            }
        }
        return retObj;
    }
}
