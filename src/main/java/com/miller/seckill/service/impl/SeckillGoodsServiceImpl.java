package com.miller.seckill.service.impl;

import com.miller.seckill.mapper.SeckillGoodsMapper;
import com.miller.seckill.service.SeckillGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by miller on 2018/8/12
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public int reduceStock(long goodsId, Date killDate) {
        return seckillGoodsMapper.reduceStock(goodsId, killDate);
    }
}
