package com.crane.apiplatformbackend.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.apiplatformbackend.constants.ErrorStatus;
import com.crane.apiplatformbackend.exception.BusinessException;
import com.crane.apiplatformbackend.model.domain.InterfaceInfo;
import com.crane.apiplatformbackend.model.domain.InterfaceInfoVo;
import com.crane.apiplatformbackend.model.request.InterfaceAddRequest;
import com.crane.apiplatformbackend.service.InterfaceInfoService;
import com.crane.apiplatformbackend.mapper.InterfaceInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Crane Resigned
 * @description 针对表【interface_info(接口信息表)】的数据库操作Service实现
 * @createDate 2024-10-05 14:56:37
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Autowired
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public boolean interfaceAdd(InterfaceAddRequest interfaceAddRequest) {

        String url = interfaceAddRequest.getUrl();
        String requestHeader = interfaceAddRequest.getRequestHeader();
        String responseHeader = interfaceAddRequest.getResponseHeader();
        if (CharSequenceUtil.hasBlank(url, requestHeader, responseHeader)) {
            throw new BusinessException(ErrorStatus.NULL_ERROR);
        }

        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setIiUrl(url);
        interfaceInfo.setIiRequestHeader(requestHeader);
        interfaceInfo.setIiResponseHeader(responseHeader);
        interfaceInfo.setIiMethod(interfaceAddRequest.getMethod());
        interfaceInfo.setIiDescription(interfaceAddRequest.getDescription());
        return interfaceInfoMapper.insert(interfaceInfo) == 1;
    }

    @Override
    public boolean interfaceDelete(Integer interfaceId) {
        return interfaceInfoMapper.deleteById(interfaceId) == 1;
    }

    @Override
    public boolean interfaceUpdate(InterfaceInfoVo interfaceInfoVo) {
        return interfaceInfoMapper.updateById(vo2Info(interfaceInfoVo)) == 1;
    }

    @Override
    public List<InterfaceInfoVo> interfaceList() {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        return interfaceInfoMapper.selectList(queryWrapper).stream().map(this::info2Vo).toList();
    }

    private InterfaceInfo vo2Info(InterfaceInfoVo interfaceInfoVo) {
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setIiId(interfaceInfoVo.getId());
        interfaceInfo.setIiDescription(interfaceInfoVo.getDescription());
        interfaceInfo.setIiUrl(interfaceInfoVo.getUrl());
        interfaceInfo.setIiMethod(interfaceInfoVo.getMethod());
        interfaceInfo.setIiRequestHeader(interfaceInfoVo.getRequestHeader());
        interfaceInfo.setIiResponseHeader(interfaceInfoVo.getResponseHeader());
        interfaceInfo.setStatus(interfaceInfoVo.getStatus());
        interfaceInfo.setCreateTime(interfaceInfoVo.getCreateTime());
        return interfaceInfo;
    }

    private InterfaceInfoVo info2Vo(InterfaceInfo interfaceInfo) {
        InterfaceInfoVo interfaceInfoVo = new InterfaceInfoVo();
        interfaceInfoVo.setId(interfaceInfo.getIiId());
        interfaceInfoVo.setDescription(interfaceInfo.getIiDescription());
        interfaceInfoVo.setUrl(interfaceInfo.getIiUrl());
        interfaceInfoVo.setMethod(interfaceInfo.getIiMethod());
        interfaceInfoVo.setRequestHeader(interfaceInfo.getIiRequestHeader());
        interfaceInfoVo.setResponseHeader(interfaceInfo.getIiResponseHeader());
        interfaceInfoVo.setStatus(interfaceInfo.getStatus());
        interfaceInfoVo.setCreateTime(interfaceInfo.getCreateTime());
        return interfaceInfoVo;
    }

}




