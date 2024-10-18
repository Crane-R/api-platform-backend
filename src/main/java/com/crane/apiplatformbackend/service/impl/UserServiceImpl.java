package com.crane.apiplatformbackend.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.apiplatformbackend.constants.ErrorStatus;
import com.crane.apiplatformbackend.constants.UserConstants;
import com.crane.apiplatformbackend.exception.BusinessException;
import com.crane.apiplatformbackend.model.domain.User;
import com.crane.apiplatformbackend.model.domain.UserVo;
import com.crane.apiplatformbackend.model.dto.UserAddRequest;
import com.crane.apiplatformbackend.service.UserService;
import com.crane.apiplatformbackend.mapper.UserMapper;
import com.crane.apiplatformbackend.util.AkSkSignGenerate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Crane Resigned
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-10-05 14:56:48
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final UserMapper userMapper;

    @Override
    public boolean userRegister(UserAddRequest userAddRequest) {
        String username = userAddRequest.getUsername();
        String password = userAddRequest.getPassword();
        String checkPassword = userAddRequest.getCheckPassword();
        if (CharSequenceUtil.hasBlank(username, password, checkPassword)) {
            throw new BusinessException(ErrorStatus.NULL_ERROR, "用户名和密码不能为空");
        }
        if (!StrUtil.equals(password, checkPassword)) {
            throw new BusinessException(ErrorStatus.PARAM_ERROR, "密码与确认密码不一致");
        }
        User user = new User();
        user.setUsername(username);
        user.setUPassword(SecureUtil.md5(password));
        user.setNickname(userAddRequest.getNickname());
        user.setUserRole(userAddRequest.getUserRole());
        String generateAk;
        //检测ak是否重复
        long existUser;
        do {
            generateAk = AkSkSignGenerate.generateAk();
            QueryWrapper<User> judgeAkRepeatWrapper = new QueryWrapper<>();
            judgeAkRepeatWrapper.eq("access_key", generateAk);
            existUser = userMapper.selectCount(judgeAkRepeatWrapper);
        } while (existUser > 0);
        user.setAccessKey(generateAk);
        user.setSecretKey(AkSkSignGenerate.generateSk());
        return userMapper.insert(user) == 1;
    }

    @Override
    public UserVo userLogin(String username, String password, HttpServletRequest request) {
        QueryWrapper<User> selectUser = new QueryWrapper<>();
        selectUser.eq("username", username);
        Long l = userMapper.selectCount(selectUser);
        if (l == 0) {
            throw new BusinessException(ErrorStatus.PARAM_ERROR, "用户名不存在");
        }
        selectUser.eq("u_password", SecureUtil.md5(password));
        User user = userMapper.selectOne(selectUser);
        if (user == null) {
            throw new BusinessException(ErrorStatus.PARAM_ERROR, "用户名或密码错误");
        } else {
            //记录登录态
            request.getSession().setAttribute(UserConstants.LOGIN_STATUS, user);
            return user2Vo(user);
        }
    }

    @Override
    public UserVo userCurrent(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(UserConstants.LOGIN_STATUS);
        if (attribute == null) {
            throw new BusinessException(ErrorStatus.NO_LOGIN);
        }
        User user = (User) attribute;
        return user2Vo(user);
    }

    private UserVo user2Vo(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getUId());
        userVo.setUsername(user.getUsername());
        userVo.setNickname(user.getNickname());
        userVo.setAvatarUrl(user.getAvatarUrl());
        userVo.setGender(user.getGender());
        userVo.setUserStatus(user.getUserStatus());
        userVo.setUserRole(user.getUserRole());
        userVo.setCreateTime(user.getCreateTime());
        userVo.setAccessKey(user.getAccessKey());
        userVo.setSecretKey(user.getSecretKey());
        return userVo;
    }

}




