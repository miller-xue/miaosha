package com.miller.seckill.service.impl;

import com.miller.seckill.domain.Order;
import com.miller.seckill.domain.SeckillOrder;
import com.miller.seckill.service.OrderService;
import com.miller.seckill.service.SeckillGoodsService;
import com.miller.seckill.service.SeckillOrderService;
import com.miller.seckill.service.SeckillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by miller on 2018/8/12
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Resource
    private SeckillGoodsService seckillGoodsService;


    @Resource
    private OrderService orderService;

    @Resource
    private SeckillOrderService seckillOrderService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order seckill(long userId, long goodsId) {
        // 记录订单
        Order order = orderService.createOrder(userId, goodsId);
        // 记录秒杀订单
        SeckillOrder seckillOrder = seckillOrderService.createSeckillOrder(userId, goodsId, order.getId());

        // 执行秒杀操作
        int i = seckillGoodsService.reduceStock(goodsId, new Date(System.currentTimeMillis()));
        if (i <= 0) {
            //秒杀失败
        }
        return order;
    }
}
