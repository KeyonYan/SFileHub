package com.keyon.sfilehub.util;

import com.keyon.sfilehub.vo.Result;
import com.keyon.sfilehub.vo.ResultEnum;

public class ResultUtil {

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.code, "success", data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(ResultEnum.FAIL.code, msg, null);
    }

    public static <T> Result<T> fail() {
        return fail(null);
    }
}
