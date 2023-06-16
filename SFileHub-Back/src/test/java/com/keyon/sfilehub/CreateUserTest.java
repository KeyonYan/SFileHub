package com.keyon.sfilehub;

import com.keyon.sfilehub.dao.RoleDao;
import com.keyon.sfilehub.dao.UserDao;
import com.keyon.sfilehub.entity.Role;
import com.keyon.sfilehub.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.util.Password;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class CreateUserTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void addRole() {
        Role role = Role.builder().title("ROLE_ADMIN").label("管理员").intro("最高权限者").enable(true).build();
        roleDao.save(role);
    }

    @Test
    public void addUser() {
        User user = User.builder().username("admin").password(passwordEncoder.encode("123456")).build();
        userDao.save(user);
    }

    @Test
    public void setAdminRole() {
        Role role = roleDao.findByTitle("ROLE_ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = userDao.findByUsername("admin");
        user.setRoles(roles);
        userDao.save(user);
    }
}
