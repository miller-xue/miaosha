package com.miller.seckill.redis;

/**
 * Created by miller on 2018/8/9
 */
public class TestUserKey extends BasePrefix {

    private TestUserKey(String prefix) {
        super(prefix);
    }


    public static TestUserKey getById = new TestUserKey("id");
    public static TestUserKey getByName = new TestUserKey("name");
}
