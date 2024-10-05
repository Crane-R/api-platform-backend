package com.crane.apiplatformbackend.model.domain;

import lombok.Data;

import java.util.Date;

/**
 * 接口信息vo
 *
 * @Date 2024/10/5 15:35
 * @Author Crane Resigned
 */
@Data
public class InterfaceInfoVo {

    private Long id;

    private String description;

    private String url;

    private Integer method;

    private String requestHeader;

    private String responseHeader;

    private Integer status;

    private Date createTime;

}
