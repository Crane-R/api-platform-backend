package com.crane.apiplatformbackend.model.dto;

import lombok.Data;

/**
 * 接口调用请求参数
 *
 * @Date 2024/10/18 09:59
 * @Author Crane Resigned
 */
@Data
public class InterfaceInvokeRequest {

     private Long interfaceId;

     private String url;

     private String requestParams;

}
