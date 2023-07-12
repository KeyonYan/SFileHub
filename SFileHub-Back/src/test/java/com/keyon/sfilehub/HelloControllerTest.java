package com.keyon.sfilehub;

import com.alibaba.fastjson2.JSONObject;
import com.keyon.sfilehub.dao.RoleDao;
import com.keyon.sfilehub.dao.UserDao;
import com.keyon.sfilehub.entity.Role;
import com.keyon.sfilehub.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HelloControllerTest {
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

    private static MultiValueMap<String, ResponseCookie> cookies;

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
        EntityExchangeResult<String> resp = webTestClient
                .post()
                .uri(url)
                .body(BodyInserters.fromValue(bodyMap))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).returnResult();
        String result = resp.getResponseBody();
        System.out.println("login res: " + result);
        JSONObject jsonResult = JSONObject.parseObject(result);
        Assertions.assertNotNull(jsonResult);
        Assertions.assertNotNull(jsonResult.get("code"));
        Assertions.assertEquals((int) jsonResult.get("code"), 0);
        cookies = resp.getResponseCookies();
        System.out.println("cookies key: " + cookies.keySet());
        Assertions.assertEquals(cookies.keySet().toString(), "[JSESSIONID]");
    }


    @Test
    @Order(4)
    @DisplayName("a hello test")
    void greetingTest() {
        System.out.println("cookies key2: " + cookies.keySet());
        String url = "http://localhost:" + port + "/hello/get";
        EntityExchangeResult<String> resp = webTestClient
                .get()
                .uri(url)
                .cookie("JSESSIONID", cookies.get("JSESSIONID").toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult();
        String result = resp.getResponseBody();
        System.out.println("hello: " + result);
    }
}