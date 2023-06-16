package com.keyon.sfilehub.controller;

import com.keyon.sfilehub.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping("/login")
    public String login(User user) {
        System.out.println(user.toString());
        return "login";
    }

}
