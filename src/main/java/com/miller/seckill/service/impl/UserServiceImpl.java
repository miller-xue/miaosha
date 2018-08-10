package com.miller.seckill.service.impl;

import com.miller.seckill.constants.SysConstants;
import com.miller.seckill.domain.User;
import com.miller.seckill.enums.UserResult;
import com.miller.seckill.exception.ParamException;
import com.miller.seckill.mapper.UserMapper;
import com.miller.seckill.praam.LoginParam;
import com.miller.seckill.redis.RedisService;
import com.miller.seckill.redis.UserKey;
import com.miller.seckill.service.UserService;
import com.miller.seckill.util.JsonUtil;
import com.miller.seckill.util.MD5Util;
import com.miller.seckill.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by miller on 2018/8/10
 * @author Miller
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;


    @Override
    public void login(LoginParam param, HttpServletResponse response) {
        // 1.常规参数校验

        // 2.判断用户是否存在
        User exist = userMapper.selectByPrimaryKey(Long.valueOf(param.getMobile()));
        if (exist == null) {
            throw new ParamException(UserResult.MOBILE_NOT_EXIST);
        }
        // 3.验证密码
        String dbPass = exist.getPassword();

        String caclPass = MD5Util.formPassToDBPass(param.getPassword(), exist.getSalt());

        if (!dbPass.equals(caclPass)) {
            throw new ParamException(UserResult.PASSWORD_ERROR);
        }
        // 生成Cookie
        String token = UUIDUtil.uuid();
        redisService.set(UserKey.token, token, exist
        );

        Cookie cookie = new Cookie(SysConstants.COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
        // session
    }

    @Override
    public User getByToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return redisService.get(UserKey.token, token, User.class);
    }
}
