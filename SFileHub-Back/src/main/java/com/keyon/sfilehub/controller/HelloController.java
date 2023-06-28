package com.keyon.sfilehub.controller;

import com.keyon.sfilehub.exception.ParameterException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

//    private StringRedisTemplate stringRedisTemplate;
//
//    public UserController(StringRedisTemplate stringRedisTemplate) {
//        this.stringRedisTemplate = stringRedisTemplate;
//    }

    @GetMapping("/get")
    public String hello() {
//        stringRedisTemplate.opsForValue().set("hello", "world");
        return "Hello World!";
    }

    @PutMapping("/put")
    public String put() {
        throw new ParameterException("Parameter Exception");
    }

    @PostMapping("/post")
    public String post() {
        return "post request";
    }

}
