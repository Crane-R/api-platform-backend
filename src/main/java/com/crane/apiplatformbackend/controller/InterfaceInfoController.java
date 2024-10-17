package com.crane.apiplatformbackend.controller;

import com.crane.apiplatformbackend.common.GeneralResponse;
import com.crane.apiplatformbackend.common.R;
import com.crane.apiplatformbackend.model.domain.InterfaceInfoVo;
import com.crane.apiplatformbackend.model.request.InterfaceAddRequest;
import com.crane.apiplatformbackend.service.InterfaceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/list")
    public GeneralResponse<List<InterfaceInfoVo>> interfaceList() {
        return R.ok(interfaceInfoService.interfaceList());
    }

}
