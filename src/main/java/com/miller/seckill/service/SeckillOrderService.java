package com.miller.seckill.service;

import com.miller.seckill.domain.SeckillOrder;

/**
 * Created by miller on 2018/8/12
 */
public interface SeckillOrderService {

    SeckillOrder getByUserIdAndGoodsId(long userId, long goodsId);

    SeckillOrder createSeckillOrder(long userId, long goodsId, long orderId);
}
