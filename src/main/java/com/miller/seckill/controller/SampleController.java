package com.miller.seckill.controller;

import com.miller.seckill.common.Result;
import com.miller.seckill.domain.User;
import com.miller.seckill.redis.RedisService;
import com.miller.seckill.redis.UserKey;
import com.miller.seckill.service.UserService;
import com.miller.seckill.util.JsonUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by miller on 2018/8/6
 */
@Controller
@RequestMapping(value = "demo")
public class SampleController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;


    @RequestMapping
    public String thymeleaf(Model model) {
        model.addAttribute("name", "Miller");
        return "hello";
    }


    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> doGet() {
        return Result.success(userService.getById(1));
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result tx() {
        userService.tx();
        return Result.success();
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
        User key1 = redisService.get(UserKey.getByName,"1", User.class);
        return Result.success(key1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result redisSet() {
        User u = new User(2, "tom");
        return Result.success(redisService.set(UserKey.getByName, "1", JsonUtil.toJSONString(u)));
    }
}
