package com.example.springboot.exception;

import lombok.Getter;

/**
 * 自定义异常
 */
@Getter
public class ServiceException extends RuntimeException {
    private String code;

    public ServiceException(String code, String msg) {
        super(msg);//父类里面的detailMessage属性
        this.code = code;
    }

}
