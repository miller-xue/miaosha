package com.miller.seckill.controller;

import com.miller.seckill.constants.SysConstants;
import com.miller.seckill.domain.User;
import com.miller.seckill.redis.RedisService;
import com.miller.seckill.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by miller on 2018/8/10
 * @author Miller
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController {

    @Autowired
    private UserService userService;
    //TODO 取出用过应是Filter 去做，放在ThreadLoacal內
    @RequestMapping(value = "/to_list")
    public String toList(@CookieValue(value = SysConstants.COOKIE_NAME_TOKEN,required = false) String cookieToken,
                         @RequestParam(value = SysConstants.COOKIE_NAME_TOKEN,required = false) String paramToken,
                         Model model) {
        if (StringUtils.isBlank(cookieToken) && StringUtils.isBlank(paramToken)) {
            return "redirect:/login/to_login";
        }
        String token = StringUtils.isBlank(paramToken) ? cookieToken : paramToken;
        User user = userService.getByToken(token);
        model.addAttribute("user", user);
        return "goods_list";
    }
}
