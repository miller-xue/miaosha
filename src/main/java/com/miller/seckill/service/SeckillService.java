package com.miller.seckill.service;

import com.miller.seckill.domain.Order;
import com.miller.seckill.domain.User;

import java.awt.image.BufferedImage;

/**
 * Created by miller on 2018/8/12
 * 秒杀服务层
 */
public interface SeckillService {

    /**
     * 秒杀
     * @param userId
     * @param goodsId
     */
    Order seckill(long userId, long goodsId);

    long getSeckillResult(long userId, long goodsId);

    boolean checkPath(User user, long id, String path);

    String getSeckillPath(long userId, long goodsId);

    BufferedImage createVerifyCode(User user, long id);

    boolean checkVerifyCode(User user, long id, int verifyCode);
}
