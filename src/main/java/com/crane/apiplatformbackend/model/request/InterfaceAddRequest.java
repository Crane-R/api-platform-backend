package com.crane.apiplatformbackend.model.request;

import lombok.Data;

/**
 * 接口新增请求体
 *
 * @Date 2024/10/5 15:15
 * @Author Crane Resigned
 */
@Data
public class InterfaceAddRequest {

    private String description;

    private String url;

    private Integer method;

    private String requestHeader;

    private String responseHeader;

}
