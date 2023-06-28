package com.keyon.sfilehub.util;

import com.keyon.sfilehub.vo.Result;
import com.keyon.sfilehub.vo.ResultEnum;

public class ResultUtil {

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.code, ResultEnum.SUCCESS.msg, data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> fail(ResultEnum resultEnum) {
        return new Result<>(resultEnum.code, resultEnum.msg, null);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> fail() {
        return fail(ResultEnum.FAIL);
    }

//    public static <T> Result<T> loginFailed() {
//        return new Result<>(ResultEnum.LOGIN_FAILED.code, ResultEnum.LOGIN_FAILED.msg, null);
//    }
//
//    public static <T> Result<T> loginExpired() {
//        return new Result<>(ResultEnum.LOGIN_EXPIRED.code, ResultEnum.LOGIN_EXPIRED.msg, null);
//    }
//
//    public static <T> Result<T> loginRequired() {
//        return new Result<>(ResultEnum.LOGIN_REQUIRED.code, ResultEnum.LOGIN_REQUIRED.msg, null);
//    }
//
//    public static <T> Result<T> unknownError() {
//        return new Result<>(ResultEnum.UNKNOWN_ERROR.code, ResultEnum.UNKNOWN_ERROR.msg, null);
//    }
}
