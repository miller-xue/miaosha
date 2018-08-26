package com.miller.seckill.access;

import com.miller.seckill.common.Result;
import com.miller.seckill.common.ThreadHolder;
import com.miller.seckill.domain.User;
import com.miller.seckill.enums.SysResult;
import com.miller.seckill.redis.AccessKey;
import com.miller.seckill.redis.RedisService;
import com.miller.seckill.util.JsonUtil;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by miller on 2018/8/26
 * TODO 抽取一个拦截器,只缓存当前的线程的Request Response CurrentUser(未登陆未null) 不涉及到业务的处理
 */
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit limit = hm.getMethodAnnotation(AccessLimit.class);
            if (limit == null) {
                return true;
            }
            int seconds = limit.seconds();
            int maxCount = limit.maxCount();
            boolean needLogin = limit.needLogin();
            User currentUser = ThreadHolder.getCurrentUser();

            String key = request.getRequestURI();
            if (needLogin) {
                if (currentUser == null) {
                    render(response, SysResult.NO_LOGIN);
                    return false;
                }
                key += "_" + currentUser.getId();
            }

            Integer count = redisService.get(AccessKey.withExpire(seconds), key, Integer.class);
            if (count == null) {
                redisService.set(AccessKey.withExpire(seconds), key, 1);
            } else if (count < maxCount) {
                redisService.incr(AccessKey.withExpire(seconds), key);
            } else {
                render(response,SysResult.SERVER_ERROR);
                return false;
            }

        }
        return true;
    }

    private void render(HttpServletResponse response, SysResult noLogin) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        @Cleanup
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JsonUtil.toJSONString(Result.error(noLogin)).getBytes("UTF-8"));
        outputStream.flush();

    }

}
