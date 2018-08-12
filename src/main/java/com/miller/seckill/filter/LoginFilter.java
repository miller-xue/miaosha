package com.miller.seckill.filter;

import com.miller.seckill.common.ThreadHolder;
import com.miller.seckill.constants.SysConstants;
import com.miller.seckill.domain.User;
import com.miller.seckill.service.UserService;
import com.miller.seckill.util.CookieUtils;
import com.miller.seckill.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by miller on 2018/7/28
 * @author Miller
 */
@Slf4j
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestURI = req.getRequestURI();

        if(req.getRequestURI().indexOf(".") > 0){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        // 1.请求URL
        String servletPath = req.getServletPath();
        if (servletPath.contains("/login/")) {
            ThreadHolder.addAll(req, resp, null);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

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

        if (user == null) {
            resp.sendRedirect("/login/to_login");
            return;
        }
        ThreadHolder.addAll(req,resp,user);
        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }
}
