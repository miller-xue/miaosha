package com.miller.seckill.service;

import com.miller.seckill.domain.User;
import com.miller.seckill.praam.LoginParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by miller on 2018/8/10
 * @author miller_xue
 */
public interface UserService {


    String login(LoginParam param, HttpServletResponse response);

    User getByToken(String token,HttpServletResponse response);

    User getById(long id);

    boolean updatePassword(String token,long id, String password);
}
