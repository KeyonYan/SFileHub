package com.keyon.sfilehub.exception;

public class BusinessException extends BaseException {

    public BusinessException(String msg) {
        super("业务逻辑错误: " + msg);
    }

}
