package com.miller.seckill.controller;

import com.miller.seckill.common.Result;
import com.miller.seckill.enums.SysResult;
import com.miller.seckill.praam.LoginParam;
import com.miller.seckill.util.JsonUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by miller on 2018/8/9
 *
 * @author Miller
 */
@RequestMapping("/login")
@Controller
@Log4j
public class LoginController {

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(LoginParam loginParam) {
        log.info(JsonUtil.toJSONString(loginParam));
        //参数校验
        String passInput = loginParam.getPassword();
        String mobile = loginParam.getMobile();
        if (StringUtils.isBlank(passInput)) {
            return Result.error(SysResult.PASSWORD_EMPTY);
        }
        if (StringUtils.isBlank(mobile)) {
            return Result.error(SysResult.MOBILE_EMPTY);
        }

        return null;
    }
}
