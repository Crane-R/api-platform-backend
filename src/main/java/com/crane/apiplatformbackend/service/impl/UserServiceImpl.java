package com.crane.apiplatformbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.apiplatformbackend.model.domain.User;
import com.crane.apiplatformbackend.service.UserService;
import com.crane.apiplatformbackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Crane Resigned
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-10-05 14:56:48
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




