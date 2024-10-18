package com.crane.apiplatformbackend.model.request;

import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 * 接口调用请求参数
 *
 * @Date 2024/10/18 09:59
 * @Author Crane Resigned
 */
@Data
public class InterfaceInvokeRequest {

     private String url;


}
