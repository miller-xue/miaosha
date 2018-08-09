package com.miller.seckill.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by miller on 2018/8/8
 */
@Getter
@AllArgsConstructor
public enum SysResult implements BaseResult {
    SUCCESS(0, "请求成功"),
    PASSWORD_EMPTY(500210, "密码不能为空"),
    MOBILE_EMPTY(500220, "手机号不能为空"),
    ;

    private int code;
    private String msg;
}
