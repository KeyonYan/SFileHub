package com.keyon.sfilehub.vo;

public enum ResultEnum {
    SUCCESS(0), // 成功
    FAIL(1), // 失败

    ARGUMENT_ERROR(10000), // 参数错误
    ARGUMENT_MISSING(10001), // 参数缺失
    ARGUMENT_TYPE_ERROR(10002), // 参数类型错误
    ARGUMENT_VALUE_ERROR(10003), // 参数值错误

    PROCESS_ERROR(20000), // 处理错误

    PERMISSION_ERROR(30000), // 权限错误
    PERMISSION_DENIED(30001), // 权限不足

    LOGIN_ERROR(40000), // 登录错误
    LOGIN_FAILED(40001), // 登录失败
    LOGIN_EXPIRED(40002), // 登录过期
    LOGIN_REQUIRED(40003), // 未登录

    UNKNOWN_ERROR(99999);

    public int code;

    ResultEnum(int code) {
        this.code = code;
    }
}
