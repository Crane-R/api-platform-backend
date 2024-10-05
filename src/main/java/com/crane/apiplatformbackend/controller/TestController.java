package com.crane.apiplatformbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动测试
 *
 * @Date 2024/10/5 14:59
 * @Author Crane Resigned
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

}
