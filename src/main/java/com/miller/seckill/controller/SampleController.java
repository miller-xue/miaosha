package com.miller.seckill.controller;

import com.miller.seckill.common.Result;
import com.miller.seckill.domain.TestUser;
import com.miller.seckill.rabbitmq.MQSender;
import com.miller.seckill.redis.RedisService;
import com.miller.seckill.redis.TestUserKey;
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
    private TestUserService testUserService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender sender;

    /*@ResponseBody
    @RequestMapping(value = "/mq")
    public Result mq() {
        sender.send("我我我我");
        return Result.success();
    }

    @ResponseBody
    @RequestMapping(value = "/mq/topic")
    public Result topic() {
        sender.sendTopic("我我我我");
        return Result.success();
    }

    @ResponseBody
    @RequestMapping(value = "/mq/fanout")
    public Result fanout() {
        sender.sendFanout("我我我我");
        return Result.success();
    }

    @ResponseBody
    @RequestMapping(value = "/mq/headers")
    public Result headers() {
        sender.sendHeaders("我我我我");
        return Result.success();
    }*/


    @RequestMapping
    public String thymeleaf(Model model) {
        model.addAttribute("name", "Miller");
        return "hello";
    }


    @RequestMapping("/db/get")
    @ResponseBody
    public Result<TestUser> doGet() {
        return Result.success(testUserService.getById(1));
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result tx() {
        testUserService.tx();
        return Result.success();
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<TestUser> redisGet() {
        TestUser key1 = redisService.get(TestUserKey.getByName,"1", TestUser.class);
        return Result.success(key1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result redisSet() {
        TestUser u = new TestUser(2, "tom");
        return Result.success(redisService.set(TestUserKey.getByName, "1", JsonUtil.toJSONString(u)));
    }
}
