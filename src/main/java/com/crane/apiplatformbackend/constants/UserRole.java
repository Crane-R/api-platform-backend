package com.crane.apiplatformbackend.constants;

import lombok.Getter;

/**
 * 用户角色枚举
 *
 * @Date 2024/10/17 13:19
 * @Author Crane Resigned
 */
@Getter
public enum UserRole {
    ADMIN(1, "管理员"),
    USER(0, "用户");

    private final Integer userRoleId;

    private final String roleName;

    UserRole(Integer userRoleId, String roleName) {
        this.userRoleId = userRoleId;
        this.roleName = roleName;
    }
}
