package com.miller.seckill.redis;

/**
 * Created by miller on 2018/8/9
 */
public class UserKey extends BasePrefix {

    private UserKey(String prefix) {
        super(prefix);
    }


    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
