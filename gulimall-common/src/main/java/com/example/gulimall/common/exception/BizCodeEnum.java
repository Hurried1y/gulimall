package com.example.gulimall.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User：Hurried1y
 * Date：2022/11/21
 * Description：
 * @author A
 */
@AllArgsConstructor
@NoArgsConstructor
public enum BizCodeEnum {
    UNKNOWN_EXCEPTION(10000, "系统未知错误"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    UNSUPPORTED_OPERATION_EXCEPTION(10002, "操作不支持错误");
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

