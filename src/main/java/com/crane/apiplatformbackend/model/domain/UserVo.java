package com.crane.apiplatformbackend.model.domain;

import lombok.Data;

import java.util.Date;

/**
 * 用户vo
 *
 * @Date 2024/10/5 16:54
 * @Author Crane Resigned
 */
@Data
public class UserVo {

    private Long id;

    private String username;

    private String nickname;

    private String avatarUrl;

    private Integer gender;

    private Integer userStatus;

    private Integer userRole;

    private String accessKey;

    private String secretKey;

    private Date createTime;

}
