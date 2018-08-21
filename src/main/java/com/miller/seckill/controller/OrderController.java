package com.miller.seckill.controller;

import com.miller.seckill.common.Result;
import com.miller.seckill.domain.User;
import com.miller.seckill.enums.SysResult;
import com.miller.seckill.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by miller on 2018/8/21
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {


    @Autowired
    private OrderService orderService;


    @RequestMapping("/detail")
    @ResponseBody
    // 可自定义注解 实现登陆拦截, 如果拦截器上有注解 未登陆就直接返回
    public Result detail(User user, @RequestParam("orderId") long id) {
        if (user == null) {
            return Result.error(SysResult.NO_LOGIN);
        }
        return Result.success(orderService.getByUserIdAndId(user.getId(), id));
    }
}
