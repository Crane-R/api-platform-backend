package com.crane.apiplatformbackend.service;

import com.crane.apiplatformbackend.model.domain.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crane.apiplatformbackend.model.domain.UserInterfaceInfoVo;
import com.crane.apiplatformbackend.model.dto.UserInterfaceAddRequest;

import java.util.List;

/**
 * @author Crane Resigned
 * @description 针对表【user_interface_info(用户接口关系表)】的数据库操作Service
 * @createDate 2024-10-18 20:12:05
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    UserInterfaceInfoVo userInterfaceInfoAdd(UserInterfaceAddRequest userInterfaceAddRequest);

    UserInterfaceInfoVo userInterfaceInfoDelete(Long id);

    UserInterfaceInfoVo userInterfaceInfoUpdate(UserInterfaceInfoVo userInterfaceInfoVo);

    List<UserInterfaceInfoVo> userInterfaceInfoList();

    UserInterfaceInfoVo userInterfaceInfo2Vo(UserInterfaceInfo userInterfaceInfo);

    UserInterfaceInfo vo2UserInterfaceInfo(UserInterfaceInfoVo userInterfaceInfoVo);

}