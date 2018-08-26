package com.miller.seckill.config;

import afu.org.checkerframework.checker.units.qual.A;
import com.miller.seckill.common.ThreadHolder;
import com.miller.seckill.constants.SysConstants;
import com.miller.seckill.domain.User;
import com.miller.seckill.service.UserService;
import com.miller.seckill.util.CookieUtils;
import com.miller.seckill.util.SpringContextUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by miller on 2018/8/11
 * @author Miller
 * 用户参数适配器
 */
@Component
public class UserArgumentResolvers implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        return parameterType == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

/*
        // TODO 可以从Filter 中的 ThreadLocal 取出对象
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        // 1.获取tokenName
        String cookieToken = CookieUtils.getCookieValue(request, SysConstants.COOKIE_NAME_TOKEN);
        String paramToken = request.getParameter(SysConstants.COOKIE_NAME_TOKEN);

        if (StringUtils.isBlank(cookieToken) && StringUtils.isBlank(paramToken)) {
            return null;
        }

        User currentUser = ThreadHolder.getCurrentUser();

        String token = StringUtils.isBlank(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(token, response);*/
        return ThreadHolder.getCurrentUser();
    }
}
