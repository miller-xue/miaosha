package com.miller.seckill.filter;

import com.miller.seckill.common.ThreadHolder;
import com.miller.seckill.constants.SysConstants;
import com.miller.seckill.domain.User;
import com.miller.seckill.service.UserService;
import com.miller.seckill.util.CookieUtils;
import com.miller.seckill.util.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by miller on 2018/8/26
 * 缓存当前线程的Request Response 和 登陆的用户
 */
public class ThreadHolderFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // 1.获取tokenName
        String cookieToken = CookieUtils.getCookieValue(req, SysConstants.COOKIE_NAME_TOKEN);
        String paramToken = req.getParameter(SysConstants.COOKIE_NAME_TOKEN);

        if (StringUtils.isBlank(cookieToken) && StringUtils.isBlank(paramToken)) {
            resp.sendRedirect("/login/to_login");
            return;
        }
        String token = StringUtils.isBlank(paramToken) ? cookieToken : paramToken;
        UserService userService = SpringContextUtil.popBean(UserService.class);
        User user = userService.getByToken(token,resp);
        ThreadHolder.addAll(req,resp,user);
        filterChain.doFilter(servletRequest, servletResponse);
        return;

    }

    @Override
    public void destroy() {
        ThreadHolder.remove();
    }
}
