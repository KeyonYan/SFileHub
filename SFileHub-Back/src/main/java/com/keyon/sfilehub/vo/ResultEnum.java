package com.keyon.sfilehub.vo;

public enum ResultEnum {
    SUCCESS(0, "success"),
    FAIL(1, "fail"),

    PARAMETER_ERROR(10000, "参数错误"),
    PARAMETER_MISSING(10001, "参数缺失"),
    PARAMETER_TYPE_ERROR(10002, "参数类型错误"),
    PARAMETER_VALUE_ERROR(10003, "参数值错误"),

    BUSINESS_ERROR(20000, "业务处理错误"),

    PERMISSION_DENIED(30000, "权限不足"),

    LOGIN_FAILED(40000, "登陆失败，请检查用户名和密码"),
    LOGIN_EXPIRED(40001, "登录过期，请重新登录"),
    LOGIN_REQUIRED(40002, "尚未登录，请先登录"),

    SYSTEM_ERROR(50000, "系统错误"),

    UNKNOWN_ERROR(99999, "未知错误");

    public int code;
    public String msg;

    ResultEnum(int code) {
        this.code = code;
    }

    ResultEnum(int code, String msg) {
        this(code);
        this.msg = msg;
    }
}
