package com.miller.seckill.redis;

import com.miller.seckill.constants.SysConstants;

/**
 * Created by miller on 2018/8/9
 */
public class UserKey extends BasePrefix {

    private UserKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }

    public static UserKey token = new UserKey(SysConstants.TOKEN_EXPIRE,SysConstants.COOKIE_NAME_TOKEN);
}
