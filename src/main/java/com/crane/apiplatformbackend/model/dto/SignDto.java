package com.crane.apiplatformbackend.model.dto;

import lombok.Data;

/**
 * 签名dto
 * sdk有
 *
 * @Date 2024/10/22 15:17
 * @Author Crane Resigned
 */
@Deprecated(forRemoval = true)
@Data
public class SignDto {

    private String accessKey;

    private Long timestamp;

    private String nonce;

    private Object data;

}
