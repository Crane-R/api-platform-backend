package com.crane.apiplatformbackend.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * aksksign生成
 *
 * @Date 2024/10/6 21:37
 * @Author Crane Resigned
 */
@Slf4j
public final class AkSkSignGenerate {

    private AkSkSignGenerate() {
    }

    public static String generateAk() {
        return RandomUtil.randomString(16);
    }

    public static String generateSk() {
        return RandomUtil.randomString(16);
    }

    /**
     * String ak, String sk, Object data, Long timestamp, String nonce
     *
     * @Author CraneResigned
     * @Date 2024/10/22 15:09
     **/
    public static String getSign(Map<String, Object> map, String secretKey) {
        map.put("data", JSONUtil.toJsonStr(map.get("data")));
        //签名记录
        log.info("签名记录：{},{}", map.toString(), secretKey);
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String content =
                MapUtil.sortJoin(map, StrUtil.EMPTY, StrUtil.EMPTY, false)
                        + StrUtil.DOT
                        + secretKey;
        return digester.digestHex(content);
    }

}
