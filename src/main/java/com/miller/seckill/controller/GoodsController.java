package com.miller.seckill.controller;

import afu.org.checkerframework.checker.units.qual.A;
import com.miller.seckill.common.Result;
import com.miller.seckill.domain.User;
import com.miller.seckill.dto.GoodsDetailDto;
import com.miller.seckill.redis.GoodsKey;
import com.miller.seckill.redis.RedisService;
import com.miller.seckill.service.GoodsService;
import com.miller.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private RedisService redisService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;


    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request,
                         HttpServletResponse response,
                         User user, Model model) {

        // 取缓存
        String catchHtml = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (StringUtils.isNotBlank(catchHtml)) {
            return catchHtml;
        }
        // 手动渲染
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.getGoodVoList();
        model.addAttribute("goodsList", goodsList);
//        return "goods_list";

        SpringWebContext context = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap(), applicationContext);
        String html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);

        if (StringUtils.isNotBlank(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result toDetail(@PathVariable("goodsId") long id,
                           Model model,
                           User user,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        // 手动渲染
        GoodsVo goods = goodsService.getGoodsVoId(id);

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

        GoodsDetailDto build = GoodsDetailDto.builder().goodsVo(goods).user(user).
                remainSeconds(remainSeconds).seckillStatus(seckillStatus).build();
        return Result.success(build);
    }

    @RequestMapping(value = "/to_detail2/{goodsId}", produces = "text/html")
    @ResponseBody
    public String toDetailByCatch(@PathVariable("goodsId") long id,
                           Model model,
                           User user,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        // 取缓存
        String catchHtml = redisService.get(GoodsKey.toDetail, "" + id, String.class);
        if (StringUtils.isNotBlank(catchHtml)) {
            return catchHtml;
        }
        // 手动渲染
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
//        return "goods_detail";


        SpringWebContext context = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap(), applicationContext);
        String html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);

        if (StringUtils.isNotBlank(html)) {
            redisService.set(GoodsKey.toDetail, "" + id, html);
        }
        return html;
    }
}
