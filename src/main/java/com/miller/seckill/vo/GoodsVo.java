package com.miller.seckill.vo;

import com.miller.seckill.domain.Goods;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by miller on 2018/8/12
 * @author Miller
 * 商品表跟商品秒杀表
 */
@Getter
@Setter
public class GoodsVo extends Goods {

    private BigDecimal seckillPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endeDate;
}
