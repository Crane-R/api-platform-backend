package com.crane.apiplatformbackend.config.interceptor;

import com.crane.apiplatformbackend.common.AuthAdmin;
import com.crane.apiplatformbackend.constants.UserConstants;
import com.crane.apiplatformbackend.constants.UserRole;
import com.crane.apiplatformcommon.constant.ErrorStatus;
import com.crane.apiplatformcommon.exception.BusinessException;
import com.crane.apiplatformcommon.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

/**
 * 鉴权拦截器
 *
 * @Date 2024/10/17 13:27
 * @Author Crane Resigned
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        //如果接口类标识了注解就直接鉴权了
        AuthAdmin controllerAnnotation = handlerMethod.getBeanType().getAnnotation(AuthAdmin.class);
        if (controllerAnnotation != null && controllerAnnotation.value()) {
            checkAdmin(request);
        }
        //到这里说明接口类注解没有拦截到，检测接口注解
        AuthAdmin methodAnnotation = handlerMethod.getMethod().getAnnotation(AuthAdmin.class);
        if (methodAnnotation != null && methodAnnotation.value()) {
            checkAdmin(request);
        }
        return true;
    }

    /**
     * 鉴权方法
     *
     * @Author CraneResigned
     * @Date 2024/10/17 13:30
     **/
    private void checkAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstants.LOGIN_STATUS);
        if (!Objects.equals(user.getUserRole(), UserRole.ADMIN.getUserRoleId())) {
            throw new BusinessException(ErrorStatus.NO_AUTHORITY, "您没有权限进行该操作");
        }
    }

}
