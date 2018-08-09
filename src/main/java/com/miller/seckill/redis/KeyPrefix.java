package com.miller.seckill.redis;

/**
 * Created by miller on 2018/8/9
 * redis前缀过期时间根接口
 * @author Miller
 */
public interface KeyPrefix {
    /**
     * 过期时间
     * @return
     */
    int expireSeconds();


    /**
     * 前缀
     * @return
     */
    String getPrefix();
}
