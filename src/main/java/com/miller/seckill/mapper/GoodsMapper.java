package com.miller.seckill.mapper;

import com.miller.seckill.domain.Goods;
import com.miller.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    List<GoodsVo> selectGoodsVoList();

    GoodsVo selectGoodsVoById(@Param("id") long id);
}