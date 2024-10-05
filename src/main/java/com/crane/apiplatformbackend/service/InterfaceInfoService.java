package com.crane.apiplatformbackend.service;

import com.crane.apiplatformbackend.model.domain.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crane.apiplatformbackend.model.domain.InterfaceInfoVo;
import com.crane.apiplatformbackend.model.request.InterfaceAddRequest;

import java.util.List;

/**
 * @author Crane Resigned
 * @description 针对表【interface_info(接口信息表)】的数据库操作Service
 * @createDate 2024-10-05 14:56:37
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    boolean interfaceAdd(InterfaceAddRequest interfaceAddRequest);

    boolean interfaceDelete(Integer interfaceId);

    boolean interfaceUpdate(InterfaceInfoVo interfaceInfoVo);

    List<InterfaceInfoVo> interfaceList();

}
