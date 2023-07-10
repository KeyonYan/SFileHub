package com.keyon.sfilehub.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Keyon
 * @data 2023/6/19 13:42
 * @desc 检验文件是否传过的vo
 */
@Data
public class CheckResultVo {
    private Boolean uploaded = false;
    private List<Integer> uploadedChunks;
}
