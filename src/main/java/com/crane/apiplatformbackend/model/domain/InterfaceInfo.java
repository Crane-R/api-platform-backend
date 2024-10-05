package com.crane.apiplatformbackend.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 接口信息表
 * @TableName interface_info
 */
@TableName(value ="interface_info")
@Data
public class InterfaceInfo implements Serializable {
    /**
     * 表id
     */
    @TableId(type = IdType.AUTO)
    private Long iiId;

    /**
     * 接口描述
     */
    private String iiDescription;

    /**
     * 接口地址
     */
    private String iiUrl;

    /**
     * 请求方式，0post，1get
     */
    private Integer iiMethod;

    /**
     * 请求头
     */
    private String iiRequestHeader;

    /**
     * 响应头
     */
    private String iiResponseHeader;

    /**
     * 接口状态，0关闭，1开启
     */
    private Integer status;

    /**
     * 
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}