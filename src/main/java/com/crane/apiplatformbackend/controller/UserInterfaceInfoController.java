package com.crane.apiplatformbackend.controller;

import com.crane.apiplatformbackend.common.GeneralResponse;
import com.crane.apiplatformbackend.common.R;
import com.crane.apiplatformcommon.exception.ExceptionUtil;
import com.crane.apiplatformcommon.model.dto.UserInterfaceAddRequest;
import com.crane.apiplatformcommon.model.vo.UserInterfaceInfoVo;
import com.crane.apiplatformcommon.service.UserInterfaceInfoService;
import com.crane.apiplatformcommon.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户接口关系接口
 *
 * @Date 2024/10/18 20:33
 * @Author Crane Resigned
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user_interface_info")
public class UserInterfaceInfoController {

    private final UserInterfaceInfoService userInterfaceInfoService;

    private final UserService userService;

    @PostMapping("/add")
    public GeneralResponse<UserInterfaceInfoVo> userInterfaceInfoAdd(Long interfaceInfoId, HttpServletRequest request) {
        Long userId = userService.userCurrent(request).getId();
        ExceptionUtil.checkNullPointException("参数为空", interfaceInfoId, userId);
        return R.ok(userInterfaceInfoService.userInterfaceInfoAdd(new UserInterfaceAddRequest(interfaceInfoId, userId)));
    }

    @PostMapping("/delete")
    public GeneralResponse<UserInterfaceInfoVo> userInterfaceInfoDelete(Long id) {
        ExceptionUtil.checkNullPointException("参数为空", id);
        return R.ok(userInterfaceInfoService.userInterfaceInfoDelete(id));
    }

    @PostMapping("/update")
    public GeneralResponse<UserInterfaceInfoVo> userInterfaceInfoUpdate(UserInterfaceInfoVo userInterfaceInfoVo) {
        return R.ok(userInterfaceInfoService.userInterfaceInfoUpdate(userInterfaceInfoVo));
    }

    @GetMapping("/list")
    public GeneralResponse<List<UserInterfaceInfoVo>> userInterfaceInfoList() {
        return R.ok(userInterfaceInfoService.userInterfaceInfoList());
    }

}
