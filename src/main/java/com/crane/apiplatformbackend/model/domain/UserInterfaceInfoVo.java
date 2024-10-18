package com.crane.apiplatformbackend.model.domain;

import lombok.Data;

import java.util.Date;

/**
 * 用户接口关系表vo
 *
 * @Date 2024/10/18 20:14
 * @Author Crane Resigned
 */
@Data
public class UserInterfaceInfoVo {

    private Long id;

    private Long userId;

    private Long interfaceId;

    private Integer totalNum;

    private Integer leftNum;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}
