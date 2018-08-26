package com.miller.seckill.redis;

/**
 * Created by miller on 2018/8/9
 */
public class AccessKey extends BasePrefix {


    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }


    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }

}
