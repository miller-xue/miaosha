package com.miller.seckill.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by miller on 2018/8/9
 */
public class ValidatorUtil {
    public static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src) {
        if (StringUtils.isBlank(src)) {
            return false;
        }
        return MOBILE_PATTERN.matcher(src).matches();
    }
}
