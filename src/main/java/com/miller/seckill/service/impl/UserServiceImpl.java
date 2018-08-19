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
 * Service 只能调用Service
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;


    @Override
    public String login(LoginParam param, HttpServletResponse response) {
        // 1.常规参数校验

        // 2.判断用户是否存在
        User exist = getById(Long.valueOf(param.getMobile()));
        if (exist == null) {
            throw new ParamException(UserResult.MOBILE_NOT_EXIST);
        }
        // 3.验证密码
        String dbPass = exist.getPassword();

        String caclPass = MD5Util.formPassToDBPass(param.getPassword(), exist.getSalt());

        if (!dbPass.equals(caclPass)) {
            throw new ParamException(UserResult.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(exist, token, response);
        return token;
    }

    @Override
    public User getByToken(String token, HttpServletResponse response) {

        if (StringUtils.isBlank(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        if (user != null) {
            addCookie(user, token, response);
        }
        // 延长token有效期

        return user;
    }

    @Override
    public User getById(long id) {
        // 取缓存
        User catchUser = redisService.get(UserKey.getById, "" + id, User.class);
        if (catchUser != null) {
            return catchUser;
        }
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            redisService.set(UserKey.getById, "" + id, user);
        }
        return user;
    }

    @Override
    public boolean updatePassword(String token,long id, String password) {
        User before = getById(id);
        if (before == null) {
            throw new ParamException(UserResult.MOBILE_NOT_EXIST);
        }
        User after = User.builder().id(id).password(MD5Util.formPassToDBPass(password, before.getSalt())).build();
        before.setPassword(after.getPassword());
        // 清除缓存
        userMapper.updateByPrimaryKeySelective(after);
        redisService.delete(UserKey.getById, String.valueOf(id));
        redisService.set(UserKey.token, token, after);
        return true;
    }

    private void addCookie(User user, String token, HttpServletResponse response) {
        redisService.set(UserKey.token, token, user);

        Cookie cookie = new Cookie(SysConstants.COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
