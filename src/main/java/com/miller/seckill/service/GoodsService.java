package com.miller.seckill.service;

import com.miller.seckill.vo.GoodsVo;

import java.util.List;

/**
 * Created by miller on 2018/8/12
 */
public interface GoodsService {

    List<GoodsVo> getGoodVoList();

    GoodsVo getGoodsVoId(long goodsId);
}
