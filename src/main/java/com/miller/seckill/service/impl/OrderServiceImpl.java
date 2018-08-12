package com.miller.seckill.service.impl;

import com.miller.seckill.domain.Order;
import com.miller.seckill.mapper.OrderMapper;
import com.miller.seckill.service.GoodsService;
import com.miller.seckill.service.OrderService;
import com.miller.seckill.vo.GoodsVo;
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

    @Override
    public Order createOrder(long userId, long goodsId) {
        GoodsVo goodsVo = goodsService.getGoodsVoId(goodsId);
        Order order = Order.builder().userId(userId).goodsId(goodsId).deliveryAddrId(1L).goodsCount(1)
                .goodsName(goodsVo.getName()).goodsPrice(goodsVo.getSeckillPrice()).orderChannel((byte) 1).status((byte) 1).createDate(new Date()).build();

        int i = orderMapper.insertSelective(order);
        return order;
    }
}
