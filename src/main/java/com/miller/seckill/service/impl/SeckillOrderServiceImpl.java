package com.miller.seckill.service.impl;

import com.miller.seckill.domain.SeckillOrder;
import com.miller.seckill.mapper.SeckillOrderMapper;
import com.miller.seckill.service.SeckillOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by miller on 2018/8/12
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Resource
    private SeckillOrderMapper seckillOrderMapper;


    @Override
    public SeckillOrder getByUserIdAndGoodsId(long userId, long goodsId) {
        return seckillOrderMapper.selectByUserIdAndGoodsId(userId,goodsId);
    }

    @Override
    public SeckillOrder createSeckillOrder(long userId, long goodsId, long orderId) {
        SeckillOrder seckillOrder = SeckillOrder.builder().goodsId(goodsId).userId(userId).orderId(orderId).build();
        int i = seckillOrderMapper.insertSelective(seckillOrder);
        return seckillOrder;
    }

}
