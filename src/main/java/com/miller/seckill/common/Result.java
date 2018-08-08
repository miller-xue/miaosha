package com.miller.seckill.common;

import com.miller.seckill.enums.BaseResult;
import com.miller.seckill.enums.SysResult;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by miller on 2018/8/8
 * @author Miller
 */
@Getter
@Setter
public class Result<T> {

    private int code;

    private String msg;

    private T data;

    /**
     * 成功时候的调用
     * */
    public static <T> Result<T> success(T data){
        return new  Result<T>(data);
    }
    public static <T> Result<T> success(){
        return new Result(null);
    }

    /**
     * 失败时候的调用
     * */
    public static <T> Result<T> error(BaseResult baseResult){
        return new  Result<T>(baseResult);
    }

    private Result(T data) {
        this.code = SysResult.SUCCESS.getCode();
        this.msg = SysResult.SUCCESS.getMsg();
        this.data = data;
    }

    private Result(BaseResult baseResult) {
        if(baseResult == null) {
            return;
        }
        this.code = baseResult.getCode();
        this.msg = baseResult.getMsg();
    }
}
