package com.keyon.sfilehub.util;

import com.keyon.sfilehub.vo.Result;
import com.keyon.sfilehub.vo.ResultEnum;

public class ResultUtil {

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS, "success", data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(ResultEnum.FAIL, msg, null);
    }

    public static <T> Result<T> fail() {
        return fail(null);
    }

    public static <T> Result<T> loginFailed() {
        return new Result<>(ResultEnum.LOGIN_FAILED, "登录失败，请检查用户名和密码！", null);
    }

    public static <T> Result<T> loginExpired() {
        return new Result<>(ResultEnum.LOGIN_EXPIRED, "登录过期，请重新登录！", null);
    }

    public static <T> Result<T> loginRequired() {
        return new Result<>(ResultEnum.LOGIN_REQUIRED, "尚未登录，请先登录！", null);
    }
}
