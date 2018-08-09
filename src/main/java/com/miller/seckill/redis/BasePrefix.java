package com.miller.seckill.redis;

import lombok.AllArgsConstructor;

/**
 * Created by miller on 2018/8/9
 * @author Miller
 * redis prefix前缀默抽象实现
 */
@AllArgsConstructor
public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;

    private String prefix;

    /**
     * 永不过期
     * @param prefix
     */
    public BasePrefix(String prefix) {
        this(0, prefix);
    }


    /**
     * 默认0 永不过期
     * @return
     */
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" +prefix;
    }
}
