package com.miller.seckill.domain;

import java.math.BigDecimal;
import java.util.Date;

public class SeckillGoods {
    private Long id;

    private Long goodsId;

    private BigDecimal seckillPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endeDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndeDate() {
        return endeDate;
    }

    public void setEndeDate(Date endeDate) {
        this.endeDate = endeDate;
    }
}