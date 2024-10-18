package com.crane.apiplatformbackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.apiplatformbackend.constants.ErrorStatus;
import com.crane.apiplatformbackend.exception.BusinessException;
import com.crane.apiplatformbackend.model.domain.InterfaceInfo;
import com.crane.apiplatformbackend.model.domain.InterfaceInfoVo;
import com.crane.apiplatformbackend.model.dto.InterfaceAddRequest;
import com.crane.apiplatformbackend.model.dto.InterfaceSelectRequest;
import com.crane.apiplatformbackend.service.InterfaceInfoService;
import com.crane.apiplatformbackend.mapper.InterfaceInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Crane Resigned
 * @description 针对表【interface_info(接口信息表)】的数据库操作Service实现
 * @createDate 2024-10-05 14:56:37
 */
@Service
@RequiredArgsConstructor
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    private final InterfaceInfoMapper interfaceInfoMapper;

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

    @Override
    public List<InterfaceInfoVo> interfaceList(InterfaceSelectRequest interfaceSelectRequest) {
        if (interfaceSelectRequest == null) {
            return interfaceList();
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        Integer status = interfaceSelectRequest.getStatus();
        if (status != null && status >= 0) {
            queryWrapper.eq("status", status);
        }
        String name = interfaceSelectRequest.getName();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("ii_description", name);
        }
        return interfaceInfoMapper.selectList(queryWrapper).stream().map(this::info2Vo).toList();
    }

    @Override
    public InterfaceInfoVo interfaceSelectOne(Long interfaceId) {
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(
                new QueryWrapper<InterfaceInfo>().eq("ii_id", interfaceId));
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorStatus.NULL_ERROR, "查询出的接口信息为空，该id不存在");
        }
        return info2Vo(interfaceInfo);
    }

    @Override
    public Page<InterfaceInfoVo> interfaceInfoPage(long pageSize, long pageNum) {
        Page<InterfaceInfo> page = super.page(new Page<>(pageNum, pageSize));
        Page<InterfaceInfoVo> pageVo = new Page<>();
        BeanUtil.copyProperties(page, pageVo);
        pageVo.setRecords(page.getRecords().stream().map(this::info2Vo).toList());
        return pageVo;
    }

    @Override
    public List<InterfaceInfoVo> interfaceAddBatch(long count) {
        List<InterfaceInfo> addList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            InterfaceInfo interfaceInfo = new InterfaceInfo();
            interfaceInfo.setIiUrl("https://www.baidu.com");
            interfaceInfo.setIiRequestHeader("请求头");
            interfaceInfo.setIiResponseHeader("响应头");
            interfaceInfo.setIiMethod(RandomUtil.randomInt(0, 2));
            interfaceInfo.setIiDescription(RandomUtil.randomString(10));
            addList.add(interfaceInfo);
        }
        super.saveBatch(addList);
        return addList.stream().map(this::info2Vo).toList();
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
        interfaceInfo.setIiRequestParams(interfaceInfoVo.getRequestParams());
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
        interfaceInfoVo.setRequestParams(interfaceInfo.getIiRequestParams());
        return interfaceInfoVo;
    }

}




