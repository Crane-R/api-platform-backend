package com.crane.apiplatformbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crane.apiplatformbackend.mapper.UserMapper;
import com.crane.apiplatformbackend.model.domain.User;
import com.crane.apiplatformbackend.util.AkSkSignGenerate;
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
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

    @GetMapping("/getSign")
    public String returnSign(String ak, String param) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ak", ak);
        User user = userMapper.selectOne(queryWrapper);
        return AkSkSignGenerate.getSign(ak, user.getSecretKey(), param);
    }

}
