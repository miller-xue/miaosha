package com.miller.seckill.controller;

import com.miller.seckill.common.Result;
import com.miller.seckill.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by miller on 2018/8/13
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @ResponseBody
    @RequestMapping(value = "/info")
    public Result info(User user) {
        return Result.success(user);
    }
}
