package com.crane.apiplatformbackend.service;

import com.crane.apiplatformbackend.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crane.apiplatformbackend.model.domain.UserVo;
import com.crane.apiplatformbackend.model.request.UserAddRequest;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author Crane Resigned
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-10-05 14:56:48
*/
public interface UserService extends IService<User> {

    boolean userRegister(UserAddRequest userAddRequest);

    boolean userLogin(String username, String password, HttpServletRequest request);

    /**
     * 获取当前登录用户的登录态
     *
     * @Author CraneResigned
     * @Date 2024/10/5 16:56
     **/
    UserVo userCurrent(HttpServletRequest request);
}
