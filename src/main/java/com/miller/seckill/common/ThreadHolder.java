package com.miller.seckill.common;

import com.miller.seckill.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by miller on 2018/7/28
 * 当前请求线程的持有者，包括当前登陆用户,当前线程 request response
 * @author Miller
 */
public class ThreadHolder {

    private static final ThreadLocal<User> userHolder = new ThreadLocal<User>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

    private static final ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<>();


    public static void addAll(HttpServletRequest request, HttpServletResponse response, User user) {
        userHolder.set(user);
        requestHolder.set(request);
        responseHolder.set(response);

    }

    public static User getCurrentUser() {
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static HttpServletResponse getCurrentResponse(){
        return responseHolder.get();}


    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
        responseHolder.remove();
    }
}
