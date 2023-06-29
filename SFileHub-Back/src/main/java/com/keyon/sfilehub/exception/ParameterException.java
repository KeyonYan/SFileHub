package com.keyon.sfilehub.exception;

public class ParameterException extends BaseException {
    public ParameterException(String msg) {
        super("参数错误: " + msg);
    }
}
