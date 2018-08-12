package com.miller.seckill.service;

import java.util.Date;

/**
 * Created by miller on 2018/8/12
 */
public interface SeckillGoodsService {
    int reduceStock(long goodsId, Date killDate);
}
