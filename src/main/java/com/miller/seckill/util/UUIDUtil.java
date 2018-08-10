package com.miller.seckill.util;

import java.util.UUID;

/**
 * Created by miller on 2018/8/10
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("_","");
    }
}
