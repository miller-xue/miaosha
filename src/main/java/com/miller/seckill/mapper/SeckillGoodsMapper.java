package com.miller.seckill.mapper;

import com.miller.seckill.domain.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface SeckillGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillGoods record);

    int insertSelective(SeckillGoods record);

    SeckillGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillGoods record);

    int updateByPrimaryKey(SeckillGoods record);


    /**
     * 秒杀减库存
     * @param goodsId
     * @param killTime
     * @return
     */
    int reduceStock(@Param("goodsId") long goodsId
            ,@Param("killTime") Date killTime);
}