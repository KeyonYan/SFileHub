package com.keyon.sfilehub.controller;

import com.keyon.sfilehub.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

//    private StringRedisTemplate stringRedisTemplate;
//
//    public UserController(StringRedisTemplate stringRedisTemplate) {
//        this.stringRedisTemplate = stringRedisTemplate;
//    }

    @GetMapping("/hello")
    public String hello() {
//        stringRedisTemplate.opsForValue().set("hello", "world");
        return "Hello World!";
    }

    @PutMapping("/put")
    public String put() {
        return "put request";
    }

}
