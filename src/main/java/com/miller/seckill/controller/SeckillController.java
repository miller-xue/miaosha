package com.miller.seckill.controller;

import afu.org.checkerframework.checker.units.qual.A;
import com.miller.seckill.domain.Order;
import com.miller.seckill.domain.SeckillOrder;
import com.miller.seckill.domain.User;
import com.miller.seckill.enums.SeckillResult;
import com.miller.seckill.service.GoodsService;
import com.miller.seckill.service.OrderService;
import com.miller.seckill.service.SeckillOrderService;
import com.miller.seckill.service.SeckillService;
import com.miller.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by miller on 2018/8/12
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/do_seckill", method = RequestMethod.POST)
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
    }
}
