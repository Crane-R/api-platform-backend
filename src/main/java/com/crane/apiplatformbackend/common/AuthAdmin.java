package com.crane.apiplatformbackend.common;

import java.lang.annotation.*;

/**
 * 权限注解
 * 在接口上标识该注解，代表需要有管理员权限才能调用
 *
 * @Date 2024/10/17 13:22
 * @Author Crane Resigned
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AuthAdmin {

    boolean value() default true;

}
