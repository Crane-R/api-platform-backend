package com.crane.apiplatformbackend.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * aksksign生成
 *
 * @Date 2024/10/6 21:37
 * @Author Crane Resigned
 */
public final class AkSkSignGenerate {

    private AkSkSignGenerate() {
    }

    public static String generateAk() {
        return RandomUtil.randomString(16);
    }

    public static String generateSk() {
        return RandomUtil.randomString(16);
    }

    public static String getSign(String ak, String sk, Object data) {
        return SecureUtil.md5(ak + sk + data);
    }

}
