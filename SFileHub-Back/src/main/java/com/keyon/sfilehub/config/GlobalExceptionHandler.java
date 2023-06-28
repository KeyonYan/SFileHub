package com.keyon.sfilehub.config;

import com.keyon.sfilehub.exception.BusinessException;
import com.keyon.sfilehub.exception.ParameterException;
import com.keyon.sfilehub.exception.PermissionException;
import com.keyon.sfilehub.exception.SystemException;
import com.keyon.sfilehub.util.ResultUtil;
import com.keyon.sfilehub.vo.Result;
import com.keyon.sfilehub.vo.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value=BusinessException.class)
    public Result businessExceptionHandler(Exception e) {
        log.error("Business Error", e);
        return ResultUtil.fail(ResultEnum.BUSINESS_ERROR.code, e.getMessage());
    }

    @ExceptionHandler(value=ParameterException.class)
    public Result parameterExceptionHandler(Exception e) {
        log.error("Parameter Error", e);
        return ResultUtil.fail(ResultEnum.PARAMETER_ERROR.code, e.getMessage());
    }

    @ExceptionHandler(value= SystemException.class)
    public Result systemExceptionHandler(Exception e) {
        log.error("System Error", e);
        return ResultUtil.fail(ResultEnum.SYSTEM_ERROR.code, e.getMessage());
    }

    @ExceptionHandler(value= PermissionException.class)
    public Result permissionExceptionHandler(Exception e) {
        log.error("Permission Error", e);
        return ResultUtil.fail(ResultEnum.PERMISSION_DENIED.code, e.getMessage());
    }

    @ExceptionHandler(value=Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error("Unknown Error", e);
        return ResultUtil.fail(ResultEnum.UNKNOWN_ERROR.code, e.getMessage());
    }


}
