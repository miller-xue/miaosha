package com.miller.seckill.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by miller on 2018/8/26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    /**
     * 多少秒
     * @return
     */
    int seconds();

    /**
     * 最多访问次数
     * @return
     */
    int maxCount();

    /**
     * 是否需要登陆,默认需要
     * @return
     */
    boolean needLogin() default true;

}
