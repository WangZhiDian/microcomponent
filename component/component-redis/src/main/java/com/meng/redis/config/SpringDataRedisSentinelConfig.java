package com.meng.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.*;
import static org.apache.commons.pool2.impl.GenericObjectPoolConfig.*;

/**
 * spring data redis sentinel config
 *
 * @author : sunyuecheng
 */
@Configuration
@PropertySource(value = "file:${config.dir}/config/redis-sentinel.properties", ignoreResourceNotFound = true)
@ConditionalOnProperty(name = "system.bean.switch.redis.sentinel", havingValue = "true", matchIfMissing = true)
public class SpringDataRedisSentinelConfig {

    @Value("${redis.pool.maxTotal}")
    private int maxTotal = DEFAULT_MAX_TOTAL;

    @Value("${redis.pool.maxIdle}")
    private int maxIdle = DEFAULT_MAX_IDLE;

    @Value("${redis.pool.minIdle}")
    private int minIdle = DEFAULT_MIN_IDLE;

    @Value("${redis.pool.lifo}")
    private boolean lifo = DEFAULT_LIFO;

    @Value("${redis.pool.fairness}")
    private boolean fairness = DEFAULT_FAIRNESS;

    @Value("${redis.pool.maxWaitMillis}")
    private long maxWaitMillis = DEFAULT_MAX_WAIT_MILLIS;

    @Value("${redis.pool.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis = DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

    @Value("${redis.pool.softMinEvictableIdleTimeMillis}")
    private long softMinEvictableIdleTimeMillis = DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

    @Value("${redis.pool.numTestsPerEvictionRun}")
    private int numTestsPerEvictionRun = DEFAULT_NUM_TESTS_PER_EVICTION_RUN;

    @Value("${redis.pool.evictionPolicyClassName}")
    private String evictionPolicyClassName = DEFAULT_EVICTION_POLICY_CLASS_NAME;

    @Value("${redis.pool.testOnCreate}")
    private boolean testOnCreate = DEFAULT_TEST_ON_CREATE;

    @Value("${redis.pool.testOnBorrow}")
    private boolean testOnBorrow = DEFAULT_TEST_ON_BORROW;

    @Value("${redis.pool.testOnBorrow}")
    private boolean testOnReturn = DEFAULT_TEST_ON_RETURN;

    @Value("${redis.pool.testWhileIdle}")
    private boolean testWhileIdle = DEFAULT_TEST_WHILE_IDLE;

    @Value("${redis.pool.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

    @Value("${redis.pool.blockWhenExhausted}")
    private boolean blockWhenExhausted = DEFAULT_BLOCK_WHEN_EXHAUSTED;

    @Value("${redis.pool.jmxEnabled}")
    private boolean jmxEnabled = DEFAULT_JMX_ENABLE;

    @Value("${redis.sentinel.master}")
    private String master;

    @Value("${redis.sentinel.host1}")
    private String host1;
    @Value("${redis.sentinel.port1}")
    private int port1;
    @Value("${redis.sentinel.host2}")
    private String host2;
    @Value("${redis.sentinel.port2}")
    private int port2;
    @Value("${redis.sentinel.host3}")
    private String host3;
    @Value("${redis.sentinel.port3}")
    private int port3;

    @Value("${redis.sentinel.connectionTimeout}")
    private int connectionTimeout;
    @Value("${redis.sentinel.password}")
    private String password;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxTotal(this.maxTotal);
        jedisPoolConfig.setMaxIdle(this.maxIdle);
        jedisPoolConfig.setMinIdle(this.minIdle);
        jedisPoolConfig.setLifo(this.lifo);
        jedisPoolConfig.setFairness(this.fairness);
        jedisPoolConfig.setMaxWaitMillis(this.maxWaitMillis);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(this.softMinEvictableIdleTimeMillis);
        jedisPoolConfig.setNumTestsPerEvictionRun(this.numTestsPerEvictionRun);
        jedisPoolConfig.setEvictionPolicyClassName(this.evictionPolicyClassName);
        jedisPoolConfig.setTestOnCreate(this.testOnCreate);
        jedisPoolConfig.setTestOnBorrow(this.testOnBorrow);
        jedisPoolConfig.setTestOnReturn(this.testOnReturn);
        jedisPoolConfig.setTestWhileIdle(this.testWhileIdle);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
        jedisPoolConfig.setBlockWhenExhausted(this.blockWhenExhausted);
        jedisPoolConfig.setJmxEnabled(this.jmxEnabled);

        return jedisPoolConfig;
    }

    /**
     * redis sentinel configuration
     *
     * @return org.springframework.data.redis.connection.RedisClusterConfiguration :
     */
    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration() {
        Set<RedisNode> redisNodeSet = new HashSet<RedisNode>();
        redisNodeSet.add(new RedisNode(host1,port1));
        redisNodeSet.add(new RedisNode(host2,port2));
        redisNodeSet.add(new RedisNode(host3,port3));

        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        redisSentinelConfiguration.setMaster(master);
        redisSentinelConfiguration.setSentinels(redisNodeSet);

        return redisSentinelConfiguration;
    }

    /**
     * jedis connection factory
     *
     * @return org.springframework.data.redis.connection.jedis.JedisConnectionFactory :
     * @throws Exception :
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration(),
                jedisPoolConfig());
        jedisConnectionFactory.setTimeout(connectionTimeout);
        jedisConnectionFactory.setPassword(password);

        return jedisConnectionFactory;
    }

    /**
     * redis template
     *
     * @return org.springframework.data.redis.core.RedisTemplate :
     * @throws Exception :
     */
    @Bean
    public RedisTemplate jedisPool() throws Exception {
        RedisTemplate redisTemplate = new RedisTemplate();

        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);

        return redisTemplate;
    }
}
