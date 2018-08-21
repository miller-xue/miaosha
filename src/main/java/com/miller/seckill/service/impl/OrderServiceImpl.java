package com.miller.seckill.service.impl;

import afu.org.checkerframework.checker.units.qual.A;
import com.miller.seckill.domain.Order;
import com.miller.seckill.enums.OrderResult;
import com.miller.seckill.exception.ParamException;
import com.miller.seckill.mapper.OrderMapper;
import com.miller.seckill.redis.OrderKey;
import com.miller.seckill.redis.RedisService;
import com.miller.seckill.service.GoodsService;
import com.miller.seckill.service.OrderService;
import com.miller.seckill.vo.GoodsVo;
import com.miller.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by miller on 2018/8/12
 *
 * @author Miller
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    @Override
    public Order createOrder(long userId, long goodsId) {
        GoodsVo goodsVo = goodsService.getGoodsVoId(goodsId);
        Order order = Order.builder().userId(userId).goodsId(goodsId).deliveryAddrId(1L).goodsCount(1)
                .goodsName(goodsVo.getName()).goodsPrice(goodsVo.getSeckillPrice()).orderChannel((byte) 1).status((byte) 1).createDate(new Date()).build();

        int i = orderMapper.insertSelective(order);
        redisService.set(OrderKey.getSeckillOrderByUIdGId, "" + userId + "_" + goodsId, order);
        return order;
    }

    @Override
    public OrderDetailVo getByUserIdAndId(long userId, long orderId) {
        Order order = orderMapper.selectByUserIdAndId(userId, orderId);
        if (order == null) {
            throw new ParamException(OrderResult.ORDER_NOT_EXIST);
        }
        Long goodsId = order.getGoodsId();
        GoodsVo goodsVoId = goodsService.getGoodsVoId(goodsId);
        return OrderDetailVo.builder().goods(goodsVoId).order(order).build();
    }
}
