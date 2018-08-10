package com.miller.seckill.exception;

import com.miller.seckill.enums.BaseResult;

/**
 * Created by miller on 2018/8/10
 */
public class ParamException extends GlobleException {

    public ParamException(String message, int code) {
        super(message, code);
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(BaseResult result) {
        super(result);
    }
}
