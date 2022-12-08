package com.example.gulimall.ware.exception;

import com.example.gulimall.common.exception.BizCodeEnum;
import com.example.gulimall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * User：Hurried1y
 * Date：2022/11/21
 * Description：
 * @author A
 */

/**
 * 集中处理所有异常
 * @author A
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.example.gulimall.ware.controller")
public class GulimallExceptionControllerAdvice {

    @ExceptionHandler(value = UnsupportedOperationException.class)
    public R unsupportedOperationExceptionHandle(){
        return R.error(BizCodeEnum.UNSUPPORTED_OPERATION_EXCEPTION.getCode(), "合并项中只能包含新建的采购项！");
    }
}
