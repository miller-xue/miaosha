package com.miller.seckill.controller;

import com.miller.seckill.access.AccessLimit;
import com.miller.seckill.common.Result;
import com.miller.seckill.domain.Order;
import com.miller.seckill.domain.User;
import com.miller.seckill.enums.SeckillResult;
import com.miller.seckill.enums.SysResult;
import com.miller.seckill.exception.ParamException;
import com.miller.seckill.rabbitmq.MQSender;
import com.miller.seckill.rabbitmq.SeckillMessage;
import com.miller.seckill.redis.AccessKey;
import com.miller.seckill.redis.RedisService;
import com.miller.seckill.redis.SeckillKey;
import com.miller.seckill.service.GoodsService;
import com.miller.seckill.service.OrderService;
import com.miller.seckill.service.SeckillOrderService;
import com.miller.seckill.service.SeckillService;
import com.miller.seckill.util.MD5Util;
import com.miller.seckill.util.UUIDUtil;
import com.miller.seckill.vo.GoodsVo;
import lombok.Cleanup;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by miller on 2018/8/12
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender sender;

    private Map<Long, Boolean> localOverMap = new HashMap<>();

    /**
     * 系统初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodVoList = goodsService.getGoodVoList();
        if (CollectionUtils.isEmpty(goodVoList)) {
            return;
        }
        for (GoodsVo goodsVo : goodVoList) {
            redisService.set(SeckillKey.getSeckillGoodsStock, "" + goodsVo.getId(), goodsVo.getStockCount());
            localOverMap.put(goodsVo.getId(), false);
        }
    }


    /*@RequestMapping(value = "/do_seckill", method = RequestMethod.POST)
    public String doSeckill(User user, @RequestParam("goodsId") long id,
                            Model model) {
        if (user == null) {
            return "redirect:/login/to_login";
        }

        // 1.判断商品是否存在
        GoodsVo goodsVoId = goodsService.getGoodsVoId(id);
        if (goodsVoId == null) {
            // 进行操作
            model.addAttribute("msg", SeckillResult.DATA_REWRITE);
            return "seckill_fail";
        }
        // 2.判断是否在秒杀时间内进行操作
        long now = System.currentTimeMillis();
        long startDate = goodsVoId.getStartDate().getTime();
        long endDate = goodsVoId.getEndeDate().getTime();
        if (now < startDate) {
            // 秒杀未开始
            model.addAttribute("msg", SeckillResult.NOT_START);
            return "seckill_fail";
        } else if (now > endDate) {
            // 秒杀结束
            model.addAttribute("msg", SeckillResult.END);
            return "seckill_fail";
        }

        // 3.判断是否有库存
        Integer stockCount = goodsVoId.getStockCount();
        if (stockCount
                <= 0) {
            model.addAttribute("msg", SeckillResult.END);
            return "seckill_fail";
        }

        // 判断是否重复秒杀
        SeckillOrder seckillOrder = seckillOrderService.getByUserIdAndGoodsId(user.getId(), id);
        if (seckillOrder != null) {
            model.addAttribute("msg", SeckillResult.REPEAT_KILL);
            return "seckill_fail";
        }
        // 减库存,下订单,写入秒杀订单
        Order order = seckillService.seckill(user.getId(), id);
        model.addAttribute("order", order);
        model.addAttribute("goods", goodsVoId);
        return "order_detail";
    }*/

    @RequestMapping(value = "/{path}/do_seckill", method = RequestMethod.POST)
    @ResponseBody
    public Result doSeckill(User user, @RequestParam("goodsId") long id,
                            @PathVariable("path") String path) {
        if (user == null) {
            return Result.error(SysResult.NO_LOGIN);
        }
        // 验证path
        boolean b = seckillService.checkPath(user, id, path);
        if (!b) {
            throw new ParamException(SeckillResult.DATA_REWRITE);
        }



        // 1.判断商品是否存在
        GoodsVo goodsVoId = goodsService.getGoodsVoId(id);
        if (goodsVoId == null) {
            // 进行操作
            return Result.error(SeckillResult.DATA_REWRITE);
        }
        // 2.判断是否在秒杀时间内进行操作
        long now = System.currentTimeMillis();
        long startDate = goodsVoId.getStartDate().getTime();
        long endDate = goodsVoId.getEndeDate().getTime();
        if (now < startDate) {
            // 秒杀未开始
            return Result.error(SeckillResult.DATA_REWRITE);
        } else if (now > endDate) {
            // 秒杀结束
            return Result.error(SeckillResult.END);
        }

        // 内存标记 商品是否被清空。
        Boolean over = localOverMap.get(id);
        if (over) {
            return Result.error(SeckillResult.END);

        }

        // 预减库存
        long decr = redisService.decr(SeckillKey.getSeckillGoodsStock, "" + id);
        if (decr < 0) {
            localOverMap.put(id, true);
            return Result.error(SeckillResult.END);

        }

        // 判断是否重复秒杀
        Order order = orderService.getByUserIdAndGoodsId(user.getId(), id);
        if (order != null) {
            return Result.error(SeckillResult.REPEAT_KILL);
        }
        // 入队
        SeckillMessage message = SeckillMessage.builder().goodsId(id).user(user).build();

        sender.sendSeckillMessage(message);


        return Result.success();

        /*// 3.判断是否有库存
        Integer stockCount = goodsVoId.getStockCount();
        if (stockCount
                <= 0) {
            return Result.error(SeckillResult.END);
        }


        // 减库存,下订单,写入秒杀订单
        Order order = seckillService.seckill(user.getId(), id);
        return Result.success(order);*/
    }

    @RequestMapping(value = "/result")
    @ResponseBody
    public Result result(User user,
                         @RequestParam("goodsId") long id) {
        if (user == null) {
            return Result.error(SysResult.NO_LOGIN);
        }
        long result = seckillService.getSeckillResult(user.getId(), id);
        return Result.success(result);
    }

    @AccessLimit(seconds = 5,maxCount = 5,needLogin = false)
    @RequestMapping(value = "/path")
    @ResponseBody
    public Result path(User user,
                       @RequestParam("goodsId") long id,
                       @RequestParam(value = "verifyCode",required = false,defaultValue = "1") int verifyCode) {
        boolean check = seckillService.checkVerifyCode(user, id, verifyCode);
        if (!check) {
            return Result.error(SysResult.SERVER_ERROR);
        }
        String str = seckillService.getSeckillPath(user.getId(), id);
        return Result.success(str);
    }

    @RequestMapping(value = "/verifyCode")
    @ResponseBody
    public Result getSeckillVerifyCode(User user,
                                       @RequestParam("goodsId") long id,
                                       HttpServletResponse response) {

        if (user == null) {
            return Result.error(SysResult.NO_LOGIN);
        }
        BufferedImage img = seckillService.createVerifyCode(user, id);
        try {
            @Cleanup
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(img, "JPEG", outputStream);
            outputStream.flush();
        } catch (Exception e) {
            return Result.error(SysResult.SERVER_ERROR);
        }
        return null;
    }



}
