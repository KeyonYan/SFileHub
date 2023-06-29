package com.keyon.sfilehub.dto;

import com.keyon.sfilehub.entity.FileTypeEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileChunkDto {
    private String filename; // 文件名
    private Integer chunkNumber; // 当前分片，从1开始
    private Long chunkSize; // 分片大小
    private Long currentChunkSize; // 当前分片大小
    private Long totalSize; // 文件总大小
    private Integer totalChunks; // 总分片数
    private String identifier; // 文件标识 md5校验码
    private MultipartFile chunkFile; // 文件

}
