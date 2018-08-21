package com.miller.seckill.service;

import com.miller.seckill.domain.Order;
import com.miller.seckill.vo.OrderDetailVo;
import com.sun.tools.corba.se.idl.constExpr.Or;

/**
 * Created by miller on 2018/8/12
 */
public interface OrderService {

    Order createOrder(long userId, long goodsId);

    OrderDetailVo getByUserIdAndId(long userId, long id);
}
