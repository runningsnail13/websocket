package com.example.springboot.exception;

import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice//当发生异常时，@ControllerAdvice注解标注的类中的异常处理方法会被触发，从而实现统一的异常处理逻辑
public class GlobalExceptionHandler {

    /**
     * 如果抛出的的是ServiceException，则调用该方法
     * @param se 业务异常
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)//表示当发生ServiceException异常时，会自动调用handle方法
    @ResponseBody //@ResponseBody注解表示将方法的返回值直接作为响应体返回给客户端，而不是跳转到某个视图页面。
    public Result handle(ServiceException se){
        return Result.error(se.getCode(), se.getMessage());
    }

}
