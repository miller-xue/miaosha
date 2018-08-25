package com.miller.seckill.service;

import com.miller.seckill.domain.Order;

/**
 * Created by miller on 2018/8/12
 * 秒杀服务层
 */
public interface SeckillService {

    /**
     * 秒杀
     * @param userId
     * @param goodsId
     */
    Order seckill(long userId, long goodsId);

    long getSeckillResult(long userId, long goodsId);
}
