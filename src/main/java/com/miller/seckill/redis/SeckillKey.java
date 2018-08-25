package com.miller.seckill.redis;

/**
 * Created by miller on 2018/8/9
 * @author Miller
 * 秒杀key
 */
public class SeckillKey extends BasePrefix {

    private SeckillKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }

    public static SeckillKey getSeckillGoodsStock = new SeckillKey(0,"gs");


    public static SeckillKey isSeckillOver = new SeckillKey(0,"isSeckillOver");
}
