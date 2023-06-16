package com.keyon.sfilehub.vo;

public enum ResultEnum {
    SUCCESS(200),
    FAIL(400),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);
    public int code;

    ResultEnum(int code) {
        this.code = code;
    }
}
