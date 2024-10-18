package com.crane.apiplatformbackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.apiplatformbackend.common.AuthAdmin;
import com.crane.apiplatformbackend.common.GeneralResponse;
import com.crane.apiplatformbackend.common.R;
import com.crane.apiplatformbackend.constants.ErrorStatus;
import com.crane.apiplatformbackend.constants.InterfaceInfoStatus;
import com.crane.apiplatformbackend.exception.BusinessException;
import com.crane.apiplatformbackend.model.domain.InterfaceInfoVo;
import com.crane.apiplatformbackend.model.domain.UserVo;
import com.crane.apiplatformbackend.model.request.InterfaceAddRequest;
import com.crane.apiplatformbackend.model.request.InterfaceInvokeRequest;
import com.crane.apiplatformbackend.model.request.InterfaceSelectRequest;
import com.crane.apiplatformbackend.service.InterfaceInfoService;
import com.crane.apiplatformbackend.service.UserService;
import com.crane.apiplatformsdk.client.ApiClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 接口信息接口
 *
 * @Date 2024/10/5 15:04
 * @Author Crane Resigned
 */
@RestController
@RequestMapping("/interface")
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
@RequiredArgsConstructor
public class InterfaceInfoController {

    private final InterfaceInfoService interfaceInfoService;

    private final UserService userService;

    /**
     * 接口新增
     *
     * @Author CraneResigned
     * @Date 2024/10/5 15:13
     **/
    @PostMapping("/add")
    public GeneralResponse<Boolean> interfaceAdd(@RequestBody InterfaceAddRequest interfaceAddRequest) {
        return R.ok(interfaceInfoService.interfaceAdd(interfaceAddRequest));
    }

    @PostMapping("/delete")
    public GeneralResponse<Boolean> interfaceDelete(Integer interfaceId) {
        return R.ok(interfaceInfoService.interfaceDelete(interfaceId));
    }

    @PostMapping("/update")
    public GeneralResponse<Boolean> interfaceUpdate(@RequestBody InterfaceInfoVo interfaceInfoVo) {
        return R.ok(interfaceInfoService.interfaceUpdate(interfaceInfoVo));
    }

    @PostMapping("/list")
    public GeneralResponse<List<InterfaceInfoVo>> interfaceList(@RequestBody InterfaceSelectRequest interfaceSelectRequest) {
        return R.ok(interfaceInfoService.interfaceList(interfaceSelectRequest));
    }

    /**
     * 上线接口
     *
     * @Author CraneResigned
     * @Date 2024/10/17 13:45
     **/
    @PostMapping("/online")
    @AuthAdmin
    public GeneralResponse<Boolean> interfaceOnLine(Long interfaceId) {
        InterfaceInfoVo interfaceInfoVo = interfaceInfoService.interfaceSelectOne(interfaceId);
        if (interfaceInfoVo == null) {
            throw new BusinessException(ErrorStatus.NULL_ERROR, "该接口不存在");
        }
        interfaceInfoVo.setStatus(InterfaceInfoStatus.ONLINE.getStatus());
        return R.ok(interfaceInfoService.interfaceUpdate(interfaceInfoVo), "接口上线成功");
    }

    @PostMapping("/offline")
    @AuthAdmin
    public GeneralResponse<Boolean> interfaceOffline(Long interfaceId) {
        InterfaceInfoVo interfaceInfoVo = interfaceInfoService.interfaceSelectOne(interfaceId);
        if (interfaceInfoVo == null) {
            throw new BusinessException(ErrorStatus.NULL_ERROR, "该接口不存在");
        }
        interfaceInfoVo.setStatus(InterfaceInfoStatus.OFFLINE.getStatus());
        return R.ok(interfaceInfoService.interfaceUpdate(interfaceInfoVo), "接口已下线");
    }

    /**
     * 在线调用接口
     *
     * @Author CraneResigned
     * @Date 2024/10/18 10:00
     **/
    @PostMapping("/invoke")
    public GeneralResponse<Object> interfaceInvoke(@RequestBody InterfaceInvokeRequest interfaceInvokeRequest, HttpServletRequest request) {
        UserVo userVo = userService.userCurrent(request);
        ApiClient apiClient = new ApiClient(userVo.getAccessKey(), userVo.getSecretKey());
        String testResult = apiClient.getTestResult(userVo.getAccessKey(), userVo.getSecretKey());
        return R.ok(testResult);
    }

    @PostMapping("/page")
    public GeneralResponse<Page<InterfaceInfoVo>> interfacePage(Long pageSize, Long current) {
        if (pageSize == null || pageSize <= 0 || current == null || current <= 0) {
            throw new BusinessException(ErrorStatus.PARAM_ERROR, "分页参数不能为空");
        }
        return R.ok(interfaceInfoService.interfaceInfoPage(pageSize, current));
    }

    /**
     * 批量生成接口
     *
     * @Author CraneResigned
     * @Date 2024/10/18 12:03
     **/
    @PostMapping("/addBatch")
    @AuthAdmin
    public GeneralResponse<List<InterfaceInfoVo>> interfaceAddBatch(Long count) {
        if (count == null || count <= 0) {
            throw new BusinessException(ErrorStatus.PARAM_ERROR, "count不能小于0");
        }
        return R.ok(interfaceInfoService.interfaceAddBatch(count));
    }

}
