package com.miller.seckill.controller;

import com.miller.seckill.common.Result;
import com.miller.seckill.param.LoginParam;
import com.miller.seckill.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by miller on 2018/8/9
 *
 * @author Miller
 */
@Log4j
@Controller
@RequestMapping("/login")
public class LoginController {


    @Autowired
    private UserService userService;


    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result doLogin(@Valid LoginParam loginParam,
                          HttpServletResponse response) {
        String token = userService.login(loginParam, response);
        // 2.登陆
        return Result.success(token);
    }
}
