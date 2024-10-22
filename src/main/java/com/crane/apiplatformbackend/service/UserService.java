package com.crane.apiplatformbackend.service;

import com.crane.apiplatformbackend.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crane.apiplatformbackend.model.domain.UserVo;
import com.crane.apiplatformbackend.model.dto.UserAddRequest;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author Crane Resigned
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-10-05 14:56:48
*/
public interface UserService extends IService<User> {

    boolean userRegister(UserAddRequest userAddRequest);

    UserVo userLogin(String username, String password, HttpServletRequest request);

    /**
     * 获取当前登录用户的登录态
     *
     * @Author CraneResigned
     * @Date 2024/10/5 16:56
     **/
    UserVo userCurrent(HttpServletRequest request);
    
    /**
     * 获取用户的secretKey，注意：该方法不能作为接口暴露出去，仅供服务器内部使用
     *
     * @Author CraneResigned
     * @Date 2024/10/22 16:30
     **/
    String userSecretKey(HttpServletRequest request);
    
    /**
     * 获取用户的accessKey，该方法可以作为接口，并且在sdk调用
     *
     * @Author CraneResigned
     * @Date 2024/10/22 17:21
     **/
    String userAccessKey(HttpServletRequest request);

}
