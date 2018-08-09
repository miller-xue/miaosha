package com.miller.seckill.redis;

import com.miller.seckill.util.JsonUtil;
import lombok.Cleanup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by miller on 2018/8/8
 * @author Miller
 * redis服务
 */
@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;


    /**
     * 获去单个对象
     * @param prefix 前缀
     * @param key key
     * @param clazz 获取的类型
     * @param <T> 类型
     * @return 实例
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        if (StringUtils.isBlank(key) || clazz == null) {
            return null;
        }
        @Cleanup Jedis jedis = jedisPool.getResource();
        // 生成真正的key
        String realKey = prefix.getPrefix() + key;
        String s = jedis.get(realKey);
        return JsonUtil.parseToClass(s, clazz);
    }


    /**
     * 设置对象
     * @param prefix 前缀
     * @param key key
     * @param value 设置的对象
     * @param <T> 对象
     * @return 是否成功
     */
    public <T> boolean set(KeyPrefix prefix,String key, T value) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        String s = JsonUtil.toJSONNoFeatures(value);
        if (StringUtils.isBlank(s)) {
            return false;
        }
        // 生成真正的key
        String realKey = prefix.getPrefix() + key;
        if (prefix.expireSeconds() <= 0) {
            jedis.set(realKey, JsonUtil.toJSONNoFeatures(value));
        }else {
            jedis.setex(realKey, prefix.expireSeconds(), JsonUtil.toJSONNoFeatures(value));
        }
        return true;
    }

    /**
     * 判断是否存在
     * @param prefix 前缀
     * @param key key
     * @return boolean 是否存在
     */
    public boolean exists(KeyPrefix prefix, String key) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        // 生成真正的key
        String realKey = prefix.getPrefix() + key;
        return jedis.exists(realKey);
    }

    /**
     * 增加值
     * @param prefix 前缀
     * @param key key
     * @return 增加后值
     */
    public Long incr(KeyPrefix prefix, String key) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        // 生成真正的key
        String realKey = prefix.getPrefix() + key;
        return jedis.incr(realKey);
    }

    /**
     * 减少值
     * @param prefix 前缀
     * @param key key
     * @return 减少后值
     */
    public Long decr(KeyPrefix prefix, String key) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        // 生成真正的key
        String realKey = prefix.getPrefix() + key;
        return jedis.decr(realKey);
    }

}
