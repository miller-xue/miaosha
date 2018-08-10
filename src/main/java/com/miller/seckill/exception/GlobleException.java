package com.miller.seckill.exception;

import com.miller.seckill.enums.BaseResult;
import lombok.Getter;

/**
 * Created by miller on 2018/8/10
 * @author Miller
 */
@Getter
public class GlobleException extends RuntimeException {

    protected int code;

    protected BaseResult baseResult;

    public GlobleException(String message, int code) {
        super(message);
        this.code = code;
    }

    public GlobleException(String message) {
        super(message);
    }

    public GlobleException(BaseResult result) {
        super(result.getMsg());
        this.code = result.getCode();
        this.baseResult = result;
    }
}
