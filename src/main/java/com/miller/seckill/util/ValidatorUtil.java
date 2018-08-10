package com.miller.seckill.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by miller on 2018/8/9
 * @author miller
 * 校验工具
 */
public class ValidatorUtil {
    public static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");

    /**
     * 正则校验手机号
     * @param src
     * @return
     */
    public static boolean isMobile(String src) {
        if (StringUtils.isBlank(src)) {
            return false;
        }
        return MOBILE_PATTERN.matcher(src).matches();
    }

    public static void main(String[] args) {
        System.out.println(isMobile("13022996276"));
    }
}
