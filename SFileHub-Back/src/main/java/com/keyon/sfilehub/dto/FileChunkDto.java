package com.keyon.sfilehub.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FileChunkDto {
    @NotNull
    private String filename; // 文件名
    @Min(1)
    private Integer chunkNumber; // 当前分片，从1开始
    @Min(1)
    private Long chunkSize; // 分片大小
    @Min(1)
    private Long currentChunkSize; // 当前分片大小
    @Min(1)
    private Long totalSize; // 文件总大小
    @Min(1)
    private Integer totalChunks; // 总分片数
    @NotBlank
    private String identifier; // 文件标识 md5校验码
    @NotNull
    private MultipartFile chunkFile; // 文件
}
