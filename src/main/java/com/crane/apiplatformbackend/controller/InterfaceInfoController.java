package com.crane.apiplatformbackend.controller;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.apiplatformbackend.common.AuthAdmin;
import com.crane.apiplatformbackend.common.GeneralResponse;
import com.crane.apiplatformbackend.common.R;
import com.crane.apiplatformcommon.constant.ErrorStatus;
import com.crane.apiplatformcommon.constant.InterfaceConstant;
import com.crane.apiplatformcommon.constant.InterfaceInfoStatus;
import com.crane.apiplatformcommon.exception.BusinessException;
import com.crane.apiplatformcommon.exception.ExceptionUtil;
import com.crane.apiplatformcommon.model.dto.InterfaceAddRequest;
import com.crane.apiplatformcommon.model.dto.InterfaceInvokeRequest;
import com.crane.apiplatformcommon.model.dto.InterfaceSelectRequest;
import com.crane.apiplatformcommon.model.vo.InterfaceInfoVo;
import com.crane.apiplatformcommon.model.vo.InvokeResponse;
import com.crane.apiplatformcommon.model.vo.UserVo;
import com.crane.apiplatformcommon.service.InterfaceInfoService;
import com.crane.apiplatformcommon.service.UserInterfaceInfoService;
import com.crane.apiplatformcommon.service.UserService;
import com.crane.apiplatformsdk.client.ApiClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 接口信息接口
 * 网关已经配置跨域，这里不需要了
 *
 * @Date 2024/10/5 15:04
 * @Author Crane Resigned
 */
@RestController
@RequestMapping("/interface")
//@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
@RequiredArgsConstructor
public class InterfaceInfoController {

    private final InterfaceInfoService interfaceInfoService;

    private final UserService userService;

    private final UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 接口新增
     *
     * @Author CraneResigned
     * @Date 2024/10/5 15:13
     **/
    @PostMapping("/add")
    public GeneralResponse<Boolean> interfaceAdd(@RequestBody InterfaceAddRequest interfaceAddRequest) {
        String url = interfaceAddRequest.getUrl();
        String requestHeader = interfaceAddRequest.getRequestHeader();
        String description = interfaceAddRequest.getDescription();
        Integer method = interfaceAddRequest.getMethod();
        String responseHeader = interfaceAddRequest.getResponseHeader();
        if (CharSequenceUtil.hasBlank(url, requestHeader, description, responseHeader) || method == null) {
            throw new BusinessException(ErrorStatus.NULL_ERROR, "参数不能为空");
        }
        if (method < 0) {
            throw new BusinessException(ErrorStatus.PARAM_ERROR, "method不能小于0");
        }
        return R.ok(interfaceInfoService.interfaceAdd(interfaceAddRequest));
    }

    @PostMapping("/delete")
    public GeneralResponse<Boolean> interfaceDelete(Integer interfaceId) {
        return R.ok(interfaceInfoService.interfaceDelete(interfaceId));
    }

    @PostMapping("/update")
    public GeneralResponse<Boolean> interfaceUpdate(@RequestBody InterfaceInfoVo interfaceInfoVo) {
        boolean b = interfaceInfoService.interfaceUpdate(interfaceInfoVo);
        return b ? R.ok(true) : R.error(ErrorStatus.SYSTEM_ERROR, false, "修改失败");
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
    public GeneralResponse<Boolean> interfaceOnLine(Long interfaceId, HttpServletRequest request) {
        InterfaceInfoVo interfaceInfoVo = interfaceInfoService.interfaceSelectOne(interfaceId);
        if (interfaceInfoVo == null) {
            throw new BusinessException(ErrorStatus.NULL_ERROR, "该接口不存在");
        }
        //要先调用一次接口才给发布
        InterfaceInvokeRequest interfaceInvokeRequest = new InterfaceInvokeRequest();
        interfaceInvokeRequest.setInterfaceId(interfaceId);
        interfaceInvokeRequest.setUrl(interfaceInfoVo.getUrl());
        interfaceInvokeRequest.setRequestParams(InterfaceConstant.ALLOW_ONLINE);
        InvokeResponse data = interfaceInvoke(interfaceInvokeRequest, request).getData();
        interfaceInfoVo.setStatus(InterfaceInfoStatus.ONLINE.getStatus());
        return R.ok(interfaceInfoService.interfaceUpdate(interfaceInfoVo),
                "接口上线成功" + data.getResult() + "剩余次数：" + data.getRemainInvokeCount());
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
    public GeneralResponse<InvokeResponse> interfaceInvoke(@RequestBody InterfaceInvokeRequest interfaceInvokeRequest,
                                                           HttpServletRequest request) {
        Long interfaceId = interfaceInvokeRequest.getInterfaceId();
        ExceptionUtil.checkNullPointException("接口id不能为空", interfaceId);

        //校验接口状态是否启动
        if (!StrUtil.equals(interfaceInvokeRequest.getRequestParams(), InterfaceConstant.ALLOW_ONLINE)
                && interfaceInfoService.getStatus(interfaceId) != InterfaceInfoStatus.ONLINE) {
            throw new BusinessException(ErrorStatus.BUSINESS_ERROR, "请先联系管理员上线接口");
        }

        Long userId = userService.userCurrent(request).getId();
        if (userInterfaceInfoService.getUserInterfaceLeftNum(userId, interfaceId) < 1) {
            throw new BusinessException(ErrorStatus.BUSINESS_ERROR, "您调用该接口的剩余次数小于1，调用失败");
        }
        //调用接口
        UserVo userVo = userService.userCurrent(request);
        InterfaceInfoVo interfaceInfoVo = interfaceInfoService.interfaceSelectOne(interfaceId);
        ApiClient apiClient = new ApiClient(userVo.getAccessKey(), userVo.getSecretKey());
//        HttpResponse invoke = apiClient.invoke(interfaceInfoVo.getUrl(), interfaceInfoVo.getMethod(), interfaceInvokeRequest.getRequestParams());
        String res = apiClient.getApiNameController(interfaceInfoVo.getUrl(), interfaceInvokeRequest.getRequestParams());
        return R.ok(new InvokeResponse(res, userInterfaceInfoService.getUserInterfaceLeftNum(userId, interfaceId)), "调用成功");
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

    /**
     * 根据id获取接口信息
     *
     * @Author CraneResigned
     * @Date 2024/10/18 16:17
     **/
    @GetMapping("/selectOne")
    public GeneralResponse<InterfaceInfoVo> interfaceSelectOne(Long interfaceId) {
        return R.ok(interfaceInfoService.interfaceSelectOne(interfaceId));
    }

    @PostMapping("/interfaceIsExistByInt")
    public GeneralResponse<Boolean> interfaceIsExist(String url, Integer method) {
        if (!(method == 0 || method == 1)) {
            throw new BusinessException(ErrorStatus.BUSINESS_ERROR, "不支持的方法");
        }
        Boolean b = interfaceInfoService.interfaceIsExist(url, method);
        return b ? R.ok(true) : R.error(ErrorStatus.BUSINESS_ERROR, "无此接口");
    }

}
