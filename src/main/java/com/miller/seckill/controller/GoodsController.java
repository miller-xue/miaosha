package com.miller.seckill.controller;

import com.miller.seckill.domain.User;
import com.miller.seckill.service.GoodsService;
import com.miller.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by miller on 2018/8/10
 * @author Miller
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/to_list")
    public String toList(User user,Model model) {
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.getGoodVoList();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping(value = "/to_detail/{goodsId}")
    public String toDetail(@PathVariable("goodsId") long id,
                           Model model,
                           User user) {
        GoodsVo goods = goodsService.getGoodsVoId(id);
        model.addAttribute("goods", goods);
        model.addAttribute("user", user);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndeDate().getTime();
        long now = System.currentTimeMillis();

        int seckillStatus = 0;
        long remainSeconds = 0;

        // 秒杀未开始,倒计时
        if (now < startAt) {
            seckillStatus = 0;
            remainSeconds = (startAt - now) / 1000;
        }
        // 秒杀结束
        else if (now > endAt) {
            seckillStatus = 2;
            remainSeconds = -1;
        }else {
            // 秒杀进行中
            seckillStatus = 1;
        }
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}
