package com.miller.seckill.service;

/**
 * Created by miller on 2018/8/12
 */
public interface SeckillOrderService {

    int createSeckillOrder(long userId, long goodsId, long orderId);
}
