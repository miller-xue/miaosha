package com.miller.seckill.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by miller on 2018/8/10
 */
@AllArgsConstructor
@Getter
public enum UserResult implements BaseResult {
    PASSWORD_EMPTY(500210, "密码不能为空"),
    MOBILE_EMPTY(500220, "手机号不能为空"),
    MOBILE_ERROR(500230,"手机号码格式错误"),
    MOBILE_NOT_EXIST(500240,"手机号不存在"),
    PASSWORD_ERROR(500250,"密码不正确")
    ;

    private int code;
    private String msg;
}
