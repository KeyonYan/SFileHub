package com.keyon.sfilehub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * @author Keyon
 * @data 2023/6/15 23:50
 * @desc 角色实体类
 */
@Entity(name = "t_role")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    //角色名
    @JsonIgnore
    @Column(nullable = false, unique = true)
    private String title;

    //显示名
    private String label;

    //角色描述
    private String intro;

    //角色是否开启
    private boolean enable = true;
}
