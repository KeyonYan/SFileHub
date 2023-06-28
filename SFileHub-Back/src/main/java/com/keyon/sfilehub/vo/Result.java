package com.keyon.sfilehub.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;
}
