package com.miller.seckill.controller;

import com.miller.seckill.domain.User;
import com.miller.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String toList(User user,Model model) {
        model.addAttribute("user", user);
        return "goods_list";
    }
}
