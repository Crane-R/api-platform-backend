package com.crane.apiplatformbackend.constants;

import lombok.Getter;

/**
 * 接口状态枚举
 *
 * @Date 2024/10/17 14:10
 * @Author Crane Resigned
 */
@Getter
public enum InterfaceInfoStatus {

    ONLINE(1, "上线"),
    OFFLINE(0, "下线");

    private final Integer status;

    private final String info;

    InterfaceInfoStatus(Integer status, String info) {
        this.status = status;
        this.info = info;
    }
}
