package com.miller.seckill.service.impl;

import com.miller.seckill.domain.Order;
import com.miller.seckill.domain.SeckillOrder;
import com.miller.seckill.redis.RedisService;
import com.miller.seckill.redis.SeckillKey;
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

    @Resource
    private RedisService redisService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order seckill(long userId, long goodsId) {
        // TODO 应该有一个暴露秒杀接口的类
        // 记录订单
        Order order = orderService.createOrder(userId, goodsId);
        // 记录秒杀订单
        int j = seckillOrderService.createSeckillOrder(userId, goodsId, order.getId());
        if (j <= 0) {
            setGoodsOver(userId , goodsId);
            return null;
        }
        int i = seckillGoodsService.reduceStock(goodsId, new Date(System.currentTimeMillis()));
        // 执行秒杀操作
        if (i <= 0) {
            //秒杀关闭
            setGoodsOver(userId , goodsId);
            return null;
        }
        return order;
    }



    @Override
    public long getSeckillResult(long userId, long goodsId) {
        Order order = orderService.getByUserIdAndGoodsId(userId, goodsId);
        if (order != null) {
            return order.getId();
        }else {
            // 没处理完
            boolean isOver = getGoodsOver(userId,goodsId);
            // 秒杀失败
            if (isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    private void setGoodsOver(long userId,long goodsId) {
        redisService.set(SeckillKey.isSeckillOver, userId + ":" + goodsId, true);
    }

    private boolean getGoodsOver(long userId, long goodsId) {
        return redisService.exists(SeckillKey.isSeckillOver, userId + ":" + goodsId);
    }

}
