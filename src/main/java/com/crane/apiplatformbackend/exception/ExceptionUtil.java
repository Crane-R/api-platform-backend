package com.crane.apiplatformbackend.exception;

import com.crane.apiplatformbackend.constants.ErrorStatus;

/**
 * 异常处理工具类
 *
 * @Date 2024/10/18 20:37
 * @Author Crane Resigned
 */
public final class ExceptionUtil {

    private ExceptionUtil() {
    }

    public static void checkNullPointException(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new BusinessException(ErrorStatus.NULL_ERROR);
            }
        }
    }

    public static void checkNullPointException(String message, Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new BusinessException(ErrorStatus.NULL_ERROR, message);
            }
        }
    }

}
