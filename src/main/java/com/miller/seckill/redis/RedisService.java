package com.miller.seckill.redis;

import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by miller on 2018/8/8
 */
@Service
public class RedisService {

    @Autowired
    private RedisConfig redisConfig;


    @Autowired
    private JedisPool jedisPool;



    public <T> T get(String key, Class<T> clazz) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        String s = jedis.get(key);
        return null;
    }

    public <T> T set(String key, T value) {
        @Cleanup Jedis resource = jedisPool.getResource();
        return null;
    }


    @Bean
    public JedisPool jedisPoolFactory() {
        // poolConfig
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);

        JedisPool jedisPool = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout() * 1000 , redisConfig.getPassword());
        return jedisPool;
    }
}
