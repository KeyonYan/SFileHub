package com.keyon.sfilehub.vo;

import lombok.Data;

@Data
public class FileStorageVo {

    private String identifier; // 唯一标识 md5
    private String fileName; // 文件名
    private String suffix; // 文件后缀
    private String fileType; // 文件类型
    private String fileLink; // 文件链接
    private Long size; // 文件大小
    private String createBy; // 创建者

}
