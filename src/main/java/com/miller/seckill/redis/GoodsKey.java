package com.miller.seckill.redis;

/**
 * Created by miller on 2018/8/9
 * @author Miller
 * 商品列表
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey toDetail = new GoodsKey(60,"detail");
}
