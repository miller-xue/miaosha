package com.miller.seckill.service.impl;

import com.miller.seckill.mapper.GoodsMapper;
import com.miller.seckill.service.GoodsService;
import com.miller.seckill.vo.GoodsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by miller on 2018/8/12
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> getGoodVoList() {
        return goodsMapper.selectGoodsVoList();
    }

    @Override
    public GoodsVo getGoodsVoId(long goodsId) {
        return goodsMapper.selectGoodsVoById(goodsId);
    }
}
