package com.miller.seckill.controller;

import com.miller.seckill.common.Result;
import com.miller.seckill.domain.TestUser;
import com.miller.seckill.redis.RedisService;
import com.miller.seckill.redis.UserKey;
import com.miller.seckill.service.TestUserService;
import com.miller.seckill.util.JsonUtil;
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
    private TestUserService userService;

    @Autowired
    private RedisService redisService;


    @RequestMapping
    public String thymeleaf(Model model) {
        model.addAttribute("name", "Miller");
        return "hello";
    }


    @RequestMapping("/db/get")
    @ResponseBody
    public Result<TestUser> doGet() {
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
    public Result<TestUser> redisGet() {
        TestUser key1 = redisService.get(UserKey.getByName,"1", TestUser.class);
        return Result.success(key1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result redisSet() {
        TestUser u = new TestUser(2, "tom");
        return Result.success(redisService.set(UserKey.getByName, "1", JsonUtil.toJSONString(u)));
    }
}
