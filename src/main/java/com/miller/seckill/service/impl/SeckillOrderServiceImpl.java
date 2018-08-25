package com.miller.seckill.service.impl;

import com.miller.seckill.domain.SeckillOrder;
import com.miller.seckill.enums.SeckillResult;
import com.miller.seckill.exception.ParamException;
import com.miller.seckill.mapper.SeckillOrderMapper;
import com.miller.seckill.redis.OrderKey;
import com.miller.seckill.redis.RedisService;
import com.miller.seckill.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by miller on 2018/8/12
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Resource
    private SeckillOrderMapper seckillOrderMapper;


    @Autowired
    private RedisService redisService;



    @Override
    public int createSeckillOrder(long userId, long goodsId, long orderId) {
        SeckillOrder seckillOrder = SeckillOrder.builder().goodsId(goodsId).userId(userId).orderId(orderId).build();
        return seckillOrderMapper.insertSelective(seckillOrder);
    }

}
