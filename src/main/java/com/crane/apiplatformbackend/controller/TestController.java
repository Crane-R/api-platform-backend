package com.crane.apiplatformbackend.controller;

import com.crane.apiplatformbackend.common.AuthAdmin;
import com.crane.apiplatformcommon.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动测试
 *
 * @Date 2024/10/5 14:59
 * @Author Crane Resigned
 */
@RestController
@RequiredArgsConstructor
public class TestController {

//    private final UserMapper userMapper;

    @GetMapping("/test")
    @AuthAdmin
    public String test() {
        return "Hello World";
    }

    @GetMapping("/test1")
    @AuthAdmin
    public String test1() {
        return "Hello World1";
    }

    @GetMapping("/getSign")
    public String returnSign(String ak, String param) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("ak", ak);
//        User user = userMapper.selectOne(queryWrapper);
//        return AkSkSignGenerate.getSign(ak, user.getSecretKey(), param);
        return null;
    }

}
