package com.keyon.sfilehub.exception;

public class PermissionException extends BaseException {

    public PermissionException(String msg) {
        super("权限错误: " + msg);
    }
}
