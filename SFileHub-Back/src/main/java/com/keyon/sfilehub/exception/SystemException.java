package com.keyon.sfilehub.exception;

public class SystemException extends BaseException {
    public SystemException(String msg) {
        super("系统错误: " + msg);
    }
}
