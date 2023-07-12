package com.keyon.sfilehub;

import com.keyon.sfilehub.dao.RoleDao;
import com.keyon.sfilehub.dao.UserDao;
import com.keyon.sfilehub.entity.Role;
import com.keyon.sfilehub.entity.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HelloControllerIT {
    @LocalServerPort
    private int port;
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Order(1)
    public void addRole() {
        Role role = Role.builder().title("ROLE_ADMIN").label("管理员").intro("最高权限者").enable(true).build();
        roleDao.save(role);
        role = Role.builder().title("ROLE_USER").label("用户").intro("普通用户").enable(true).build();
        roleDao.save(role);
    }

    @Test
    @Order(2)
    public void addAdmin() {
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

    @Test
    @Order(3)
    @DisplayName("login admin")
    void login() {
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("username", "admin");
        bodyMap.put("password", "123456");
        String url = "http://localhost:" + port + "/login";
        EntityExchangeResult<String> result = this.webTestClient
                .post()
                .uri(url)
                .body(BodyInserters.fromValue(bodyMap))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).returnResult();
        System.out.println("login res: " + result.getResponseBody());
    }


    @Test
    @Order(4)
    @DisplayName("a hello test")
    void greetingTets() {
        String url = "http://localhost:" + port + "/hello/get";
        this.webTestClient
                .get()
                .uri(url)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class);
    }
}