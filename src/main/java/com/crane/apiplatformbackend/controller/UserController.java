package com.crane.apiplatformbackend.controller;

import com.crane.apiplatformbackend.common.GeneralResponse;
import com.crane.apiplatformbackend.common.R;
import com.crane.apiplatformbackend.model.domain.UserVo;
import com.crane.apiplatformbackend.model.request.UserAddRequest;
import com.crane.apiplatformbackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户接口
 *
 * @Date 2024/10/5 16:35
 * @Author Crane Resigned
 */
@RestController
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public GeneralResponse<Boolean> userRegister(@RequestBody UserAddRequest userAddRequest) {
        return R.ok(userService.userRegister(userAddRequest));
    }

    @PostMapping("/login")
    public GeneralResponse<Boolean> userLogin(String username, String password, HttpServletRequest request) {
        return R.ok(userService.userLogin(username, password, request), "登录成功，欢迎你，" + username);
    }

    @GetMapping("/current")
    public GeneralResponse<UserVo> userCurrent(HttpServletRequest request) {
        return R.ok(userService.userCurrent(request));
    }

}