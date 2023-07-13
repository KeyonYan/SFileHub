package com.keyon.sfilehub.entity;

public enum FileTypeEnum {
    IMAGE("IMAGE"),
    VIDEO("VIDEO"),
    AUDIO("AUDIO"),
    DOCUMENT("DOCUMENT"),
    OTHER("OTHER");

    String value;

    FileTypeEnum(String value) {
        this.value = value;
    }
}
