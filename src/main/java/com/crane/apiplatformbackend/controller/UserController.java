package com.crane.apiplatformbackend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.crane.apiplatformbackend.common.GeneralResponse;
import com.crane.apiplatformbackend.common.R;
import com.crane.apiplatformbackend.model.domain.UserVo;
import com.crane.apiplatformbackend.model.dto.SignDto;
import com.crane.apiplatformbackend.model.dto.UserAddRequest;
import com.crane.apiplatformbackend.service.UserService;
import com.crane.apiplatformbackend.util.AkSkSignGenerate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户接口
 *
 * @Date 2024/10/5 16:35
 * @Author Crane Resigned
 */
@RestController
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public GeneralResponse<Boolean> userRegister(@RequestBody UserAddRequest userAddRequest) {
        return R.ok(userService.userRegister(userAddRequest));
    }

    @PostMapping("/login")
    public GeneralResponse<UserVo> userLogin(String username, String password, HttpServletRequest request) {
        return R.ok(userService.userLogin(username, password, request), "登录成功，欢迎你，" + username);
    }

    @GetMapping("/current")
    public GeneralResponse<UserVo> userCurrent(HttpServletRequest request) {
        return R.ok(userService.userCurrent(request));
    }

    /**
     * 获取用户签名
     *
     * @Author CraneResigned
     * @Date 2024/10/22 15:02
     **/
    @PostMapping("/getSign")
    public GeneralResponse<String> getSign(@RequestBody SignDto signDto, HttpServletRequest request) {
        Map<String, Object> signMap = BeanUtil.beanToMap(signDto);
        String sk = userService.userSecretKey(request);
        return R.ok(AkSkSignGenerate.getSign(signMap, sk));
    }

    @GetMapping("/userAk")
    public GeneralResponse<String> userAccessKey(HttpServletRequest request) {
        return R.ok(userService.userAccessKey(request));
    }

}
