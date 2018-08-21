package com.miller.seckill.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by miller on 2018/8/10
 */
@AllArgsConstructor
@Getter
public enum OrderResult implements BaseResult {
    ORDER_NOT_EXIST(600101, "订单不存在"),
    ;

    private int code;
    private String msg;
}
