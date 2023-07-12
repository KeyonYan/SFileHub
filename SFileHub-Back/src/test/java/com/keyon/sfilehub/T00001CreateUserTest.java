package com.keyon.sfilehub;

import com.keyon.sfilehub.dao.RoleDao;
import com.keyon.sfilehub.dao.UserDao;
import com.keyon.sfilehub.entity.Role;
import com.keyon.sfilehub.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T00001CreateUserTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Order(1)
    public void addRole() {
        System.out.println("11111");
        if (roleDao.findByTitle("ROLE_ADMIN") != null) return;
        Role role = Role.builder().title("ROLE_ADMIN").label("管理员").intro("最高权限者").enable(true).build();
        roleDao.save(role);
        if (roleDao.findByTitle("ROLE_USER") != null) return;
        Role role2 = Role.builder().title("ROLE_USER").label("用户").intro("普通用户").enable(true).build();
        roleDao.save(role2);
    }

    @Test
    @Order(2)
    public void addAdmin() {
        if (userDao.findByUsername("admin") != null) return;
        User user = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("123456"))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
        Role adminRole = roleDao.findByTitle("ROLE_ADMIN");
        Role userRole = roleDao.findByTitle("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        roles.add(userRole);
        user.setRoles(roles);
        userDao.save(user);
    }

    @Test
    @Order(2)
    public void addUser() {
        if (userDao.findByUsername("user") != null) return;
        User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("123456"))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
        Set<Role> roles = new HashSet<>();
        Role userRole = roleDao.findByTitle("ROLE_USER");
        roles.add(userRole);
        user.setRoles(roles);
        userDao.save(user);
    }
}
