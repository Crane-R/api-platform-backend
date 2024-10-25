package com.crane.apiplatformbackend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crane.apiplatformcommon.model.domain.UserInterfaceInfo;

import java.util.List;

/**
* @author Crane Resigned
* @description 针对表【user_interface_info(用户接口关系表)】的数据库操作Mapper
* @createDate 2024-10-18 20:12:05
* @Entity com.crane.apiplatformbackend.model.domain.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

}




