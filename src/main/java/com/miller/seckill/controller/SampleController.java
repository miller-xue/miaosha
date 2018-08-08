package com.miller.seckill.controller;

import com.miller.seckill.common.Result;
import com.miller.seckill.domain.User;
import com.miller.seckill.service.UserService;
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
}
