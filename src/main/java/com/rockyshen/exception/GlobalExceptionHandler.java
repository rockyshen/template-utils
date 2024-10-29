package com.rockyshen.exception;


import com.rockyshen.result_v2.Result;
import com.rockyshen.result_v2.ResultUtils;
import com.rockyshen.result_v2.StatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


/**
 * 全局异常处理类
 * @author rockyshen
 * @date 2024/9/10 14:34
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 只捕获业务相关异常
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result businessException(BusinessException e, HttpServletRequest request){
//        log.error("businessException:"+e.getMessage(),e);
        log.error("请求：{}，发送异常：{}",request.getRequestURL(),e.getMessage(),e);
        return ResultUtils.fail(e.getCode(),e.getMessage(),e.getDescription());
    }

    /**
     * 捕获其他RuntimeException
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result runtimeException(RuntimeException e){
        log.error("runtimeException",e);
        return ResultUtils.fail(StatusCodeEnum.SYSTEM_ERROR,"系统内部错误");
    }
}
