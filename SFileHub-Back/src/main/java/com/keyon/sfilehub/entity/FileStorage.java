package com.keyon.sfilehub.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "t_file_storage")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileStorage extends BaseEntity {
    private String fileName; // 文件名
    private String fileLink; // 文件链接
    private String suffix; // 文件后缀
    private String fileType; // 文件类型
    private Long size; // 文件大小
    private String identifier; // 唯一标识 md5
    @ManyToOne
    private User createBy; // 创建者
}
