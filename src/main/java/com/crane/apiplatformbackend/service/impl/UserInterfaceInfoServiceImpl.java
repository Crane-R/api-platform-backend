package com.crane.apiplatformbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.apiplatformbackend.constants.ErrorStatus;
import com.crane.apiplatformbackend.exception.BusinessException;
import com.crane.apiplatformbackend.exception.ExceptionUtil;
import com.crane.apiplatformbackend.mapper.InterfaceInfoMapper;
import com.crane.apiplatformbackend.mapper.UserMapper;
import com.crane.apiplatformbackend.model.domain.InterfaceInfo;
import com.crane.apiplatformbackend.model.domain.User;
import com.crane.apiplatformbackend.model.domain.UserInterfaceInfo;
import com.crane.apiplatformbackend.model.domain.UserInterfaceInfoVo;
import com.crane.apiplatformbackend.model.dto.UserInterfaceAddRequest;
import com.crane.apiplatformbackend.service.UserInterfaceInfoService;
import com.crane.apiplatformbackend.mapper.UserInterfaceInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Crane Resigned
 * @description 针对表【user_interface_info(用户接口关系表)】的数据库操作Service实现
 * @createDate 2024-10-18 20:12:05
 */
@Service
@RequiredArgsConstructor
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    private final UserMapper userMapper;

    private final InterfaceInfoMapper interfaceInfoMapper;

    private final UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public UserInterfaceInfoVo userInterfaceInfoAdd(UserInterfaceAddRequest userInterfaceAddRequest) {
        Long interfaceId = userInterfaceAddRequest.getInterfaceId();
        Long userId = userInterfaceAddRequest.getUserId();
        if (interfaceId == null || userId == null) {
            throw new BusinessException(ErrorStatus.NULL_ERROR, "接口id和用户id不能为空");
        }
        if (interfaceId < 0 || userId < 0) {
            throw new BusinessException(ErrorStatus.PARAM_ERROR, "接口id和用户id不能小于0");
        }
        User user = userMapper.selectById(userId);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectById(interfaceId);
        if (user == null || interfaceInfo == null) {
            throw new BusinessException(ErrorStatus.PARAM_ERROR, "接口或用户不存在");
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        userInterfaceInfo.setIiId(interfaceId);
        userInterfaceInfo.setUId(userId);
        userInterfaceInfo.setLeftNum(10);
        userInterfaceInfo.setTotalNum(10);
        int insert = userInterfaceInfoMapper.insert(userInterfaceInfo);
        if (insert != 1) {
            throw new BusinessException(ErrorStatus.SYSTEM_ERROR, "新增失败");
        }
        return userInterfaceInfo2Vo(userInterfaceInfo);
    }

    @Override
    public UserInterfaceInfoVo userInterfaceInfoDelete(Long id) {
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.selectById(id);
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorStatus.PARAM_ERROR, "删除的用户接口关系不存在");
        }
        int i = userInterfaceInfoMapper.deleteById(id);
        if (i != 1) {
            throw new BusinessException(ErrorStatus.SYSTEM_ERROR, "删除失败");
        }
        return userInterfaceInfo2Vo(userInterfaceInfo);
    }

    @Override
    public UserInterfaceInfoVo userInterfaceInfoUpdate(UserInterfaceInfoVo userInterfaceInfoVo) {
        int i = userInterfaceInfoMapper.updateById(vo2UserInterfaceInfo(userInterfaceInfoVo));
        if (i != 1) {
            throw new BusinessException(ErrorStatus.SYSTEM_ERROR, "修改失败");
        }
        return userInterfaceInfo2Vo(userInterfaceInfoMapper.selectById(userInterfaceInfoVo.getId()));
    }

    @Override
    public List<UserInterfaceInfoVo> userInterfaceInfoList() {
        return userInterfaceInfoMapper.selectList(new QueryWrapper<>()).stream()
                .map(this::userInterfaceInfo2Vo).toList();
    }

    @Override
    public UserInterfaceInfoVo userInterfaceInfo2Vo(UserInterfaceInfo userInterfaceInfo) {
        UserInterfaceInfoVo userInterfaceInfoVo = new UserInterfaceInfoVo();
        userInterfaceInfoVo.setId(userInterfaceInfo.getUiiId());
        userInterfaceInfoVo.setUserId(userInterfaceInfo.getUId());
        userInterfaceInfoVo.setInterfaceId(userInterfaceInfo.getIiId());
        userInterfaceInfoVo.setTotalNum(userInterfaceInfo.getTotalNum());
        userInterfaceInfoVo.setLeftNum(userInterfaceInfo.getLeftNum());
        userInterfaceInfoVo.setStatus(userInterfaceInfo.getStatus());
        userInterfaceInfoVo.setCreateTime(userInterfaceInfo.getCreateTime());
        userInterfaceInfoVo.setUpdateTime(userInterfaceInfo.getUpdateTime());
        return userInterfaceInfoVo;
    }

    @Override
    public UserInterfaceInfo vo2UserInterfaceInfo(UserInterfaceInfoVo userInterfaceInfoVo) {
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        userInterfaceInfo.setUiiId(userInterfaceInfoVo.getId());
        userInterfaceInfo.setUId(userInterfaceInfoVo.getUserId());
        userInterfaceInfo.setIiId(userInterfaceInfoVo.getInterfaceId());
        userInterfaceInfo.setTotalNum(userInterfaceInfoVo.getTotalNum());
        userInterfaceInfo.setLeftNum(userInterfaceInfoVo.getLeftNum());
        userInterfaceInfo.setStatus(userInterfaceInfoVo.getStatus());
        userInterfaceInfo.setCreateTime(userInterfaceInfoVo.getCreateTime());
        userInterfaceInfo.setUpdateTime(userInterfaceInfoVo.getUpdateTime());
        return userInterfaceInfo;
    }

    @Override
    public Integer getUserInterfaceLeftNum(Long userId, Long interfaceId) {
        QueryWrapper<UserInterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        interfaceInfoQueryWrapper.eq("ii_id", interfaceId);
        interfaceInfoQueryWrapper.eq("u_id", userId);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.selectOne(interfaceInfoQueryWrapper);
        ExceptionUtil.checkNullPointException("该用户没有对应的接口调用数据", userInterfaceInfo);
        return userInterfaceInfo.getLeftNum();
    }
}




