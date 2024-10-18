package com.crane.apiplatformbackend.model.dto;

import lombok.Data;

/**
 * 用户接口添加请求体
 *
 * @Date 2024/10/18 20:16
 * @Author Crane Resigned
 */
@Data
public class UserInterfaceAddRequest {

     private Long userId;

     private Long interfaceId;

}
