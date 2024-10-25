package com.crane.apiplatformbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crane.apiplatformbackend.common.AuthAdmin;
import com.crane.apiplatformbackend.common.GeneralResponse;
import com.crane.apiplatformbackend.common.R;
import com.crane.apiplatformbackend.mapper.InterfaceInfoMapper;
import com.crane.apiplatformbackend.mapper.UserInterfaceInfoMapper;
import com.crane.apiplatformcommon.model.domain.InterfaceInfo;
import com.crane.apiplatformcommon.model.domain.UserInterfaceInfo;
import com.crane.apiplatformcommon.model.vo.InterfaceInfoVo;
import com.crane.apiplatformcommon.service.InterfaceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统计调用接口
 *
 * @Date 2024/10/25 16:07
 * @Author Crane Resigned
 */
@RestController
@RequestMapping("/analysis")
@RequiredArgsConstructor
public class AnalysisController {


    private final UserInterfaceInfoMapper userInterfaceInfoMapper;

    private final InterfaceInfoMapper interfaceInfoMapper;

    private final InterfaceInfoService interfaceInfoService;

    @RequestMapping("/top/interface/invoke")
    @AuthAdmin
    public GeneralResponse<List<InterfaceInfoVo>> listTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(6);
        Map<Long, List<UserInterfaceInfo>> collect =
                userInterfaceInfos.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getIiId));
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("ii_id", collect.keySet());
        List<InterfaceInfoVo> list = interfaceInfoMapper.selectList(queryWrapper)
                .stream().map(e -> {
                    InterfaceInfoVo interfaceInfoVo = interfaceInfoService.info2Vo(e);
                    interfaceInfoVo.setTotalNum(collect.get(interfaceInfoVo.getId()).getFirst().getTotalNum());
                    return interfaceInfoVo;
                }).toList();
        return R.ok(list);
    }

}
