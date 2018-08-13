package com.miller.seckill.controller;

import com.miller.seckill.common.Result;
import com.miller.seckill.enums.SysResult;
import com.miller.seckill.enums.UserResult;
import com.miller.seckill.praam.LoginParam;
import com.miller.seckill.service.UserService;
import com.miller.seckill.util.JsonUtil;
import com.miller.seckill.util.ValidatorUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
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
        userService.login(loginParam,response);
        // 2.登陆
        return Result.success();
    }
}
