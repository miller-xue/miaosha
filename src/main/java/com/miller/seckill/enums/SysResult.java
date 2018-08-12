package com.miller.seckill.enums;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by miller on 2018/8/8
 */
@Getter
@AllArgsConstructor
public enum SysResult implements BaseResult {
    SUCCESS(0, "请求成功"),
    SERVER_ERROR(1, "服务器忙"),
    BIND_ERROR(2, "参数校验异常:%s"),
    ;

    private int code;
    private String msg;

    public SysResult fillArgs(Object... args) {
        this.msg = String.format(this.msg, args);
        return this;
    }
}
