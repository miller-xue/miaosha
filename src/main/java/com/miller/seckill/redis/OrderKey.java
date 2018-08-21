package com.miller.seckill.redis;

/**
 * Created by miller on 2018/8/9
 */
public class OrderKey extends BasePrefix {

    private OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getSeckillOrderByUIdGId = new OrderKey("seckillOrderByUIdGId");
}
