package com.miller.seckill.service.impl;

import com.miller.seckill.domain.Order;
import com.miller.seckill.domain.SeckillOrder;
import com.miller.seckill.domain.User;
import com.miller.seckill.redis.RedisService;
import com.miller.seckill.redis.SeckillKey;
import com.miller.seckill.service.OrderService;
import com.miller.seckill.service.SeckillGoodsService;
import com.miller.seckill.service.SeckillOrderService;
import com.miller.seckill.service.SeckillService;
import com.miller.seckill.util.MD5Util;
import com.miller.seckill.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;

/**
 * Created by miller on 2018/8/12
 */

@Service
public class SeckillServiceImpl implements SeckillService {

    @Resource
    private SeckillGoodsService seckillGoodsService;


    @Resource
    private OrderService orderService;

    @Resource
    private SeckillOrderService seckillOrderService;

    @Resource
    private RedisService redisService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order seckill(long userId, long goodsId) {
        // TODO 应该有一个暴露秒杀接口的类
        // 记录订单
        Order order = orderService.createOrder(userId, goodsId);
        // 记录秒杀订单
        int j = seckillOrderService.createSeckillOrder(userId, goodsId, order.getId());
        if (j <= 0) {
            setGoodsOver(userId , goodsId);
            return null;
        }
        int i = seckillGoodsService.reduceStock(goodsId, new Date(System.currentTimeMillis()));
        // 执行秒杀操作
        if (i <= 0) {
            //秒杀关闭
            setGoodsOver(userId , goodsId);
            return null;
        }
        return order;
    }



    @Override
    public long getSeckillResult(long userId, long goodsId) {
        Order order = orderService.getByUserIdAndGoodsId(userId, goodsId);
        if (order != null) {
            return order.getId();
        }else {
            // 没处理完
            boolean isOver = getGoodsOver(userId,goodsId);
            // 秒杀失败
            if (isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    @Override
    public boolean checkPath(User user, long id, String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        }
        String s = redisService.get(SeckillKey.getSeckillPath, user.getId() + "_" + id, String.class);
        return s.equals(path);
    }

    @Override
    public String getSeckillPath(long userId, long goodsId) {
        String str = MD5Util.md5(UUIDUtil.uuid() + "12345");
        redisService.set(SeckillKey.getSeckillPath, userId + "_" + goodsId, str);
        return str;
    }

    private void setGoodsOver(long userId,long goodsId) {
        redisService.set(SeckillKey.isSeckillOver, userId + ":" + goodsId, true);
    }

    private boolean getGoodsOver(long userId, long goodsId) {
        return redisService.exists(SeckillKey.isSeckillOver, userId + ":" + goodsId);
    }

    @Override
    public BufferedImage createVerifyCode(User user, long goodsId) {
        if(user == null || goodsId <=0) {
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(SeckillKey.getSeckillVerifyCode, user.getId() + "_" + goodsId, rnd);
        //输出图片
        return image;
    }

    @Override
    public boolean checkVerifyCode(User user, long id, int verifyCode) {
        Integer integer = redisService.get(SeckillKey.getSeckillVerifyCode, user.getId() + "_" + id, Integer.class);
        if (integer == null || integer - verifyCode != 0) {
            return false;
        }
        redisService.delete(SeckillKey.getSeckillVerifyCode, user.getId() + "_" + id);
        return true;
    }


    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[] {'+', '-', '*'};
    /**
     * + - *
     * */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }
}
