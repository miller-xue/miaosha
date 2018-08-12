package com.miller.seckill.mapper;

import com.miller.seckill.domain.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SeckillOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillOrder record);

    int insertSelective(SeckillOrder record);

    SeckillOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillOrder record);

    int updateByPrimaryKey(SeckillOrder record);

    SeckillOrder selectByUserIdAndGoodsId(@Param("userId") long userId,@Param("goodsId") long goodsId);
}