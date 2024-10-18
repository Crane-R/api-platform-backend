package com.crane.apiplatformbackend.model.dto;

import lombok.Data;

/**
 * 用户新增注册请求体
 *
 * @Date 2024/10/5 16:36
 * @Author Crane Resigned
 */
@Data
public class UserAddRequest {

    private String username;

    private String nickname;

    private String password;

    private String checkPassword;

    private Integer userRole;

}
