package com.keyon.sfilehub.entity;

import com.keyon.sfilehub.entity.Role;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Keyon
 * @data 2023/6/15 23:51
 * @desc 用户实体类
 */
@Entity(name = "t_user")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails, CredentialsContainer {
    private static final long serialVersionUID = -2345678901234567890L;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    private boolean enabled; // 账号是否可用
    private boolean accountNonExpired; // 账号是否过期
    private boolean accountNonLocked; // 账号是否锁定
    private boolean credentialsNonExpired; // 账号凭证是否过期
    private Date lastLoginTime; // 最后登录时间
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE) // 立即从数据库中进行加载数据
    @Column(nullable = false)
    private Set<Role> roles;

    /**
     * 获取用户角色信息
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = this.getRoles();
        if (roles == null || roles.isEmpty()) {
            return Collections.emptySet();
        }
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getTitle())).collect(Collectors.toSet());
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}




