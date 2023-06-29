package com.keyon.sfilehub.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "t_file_chunk")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileChunk extends BaseEntity {
    private String fileName; // 文件名
    private FileTypeEnum fileType; // 文件类型
    private Integer chunkNumber; // 当前分片，从1开始
    private Long chunkSize; // 分片大小
    private Long currentChunkSize; // 当前分片大小
    private Long totalSize; // 文件总大小
    private Integer totalChunks; // 总分片数
    private String identifier; // 文件标识 md5校验码
    @ManyToOne
    private User createBy; // 创建者
}
