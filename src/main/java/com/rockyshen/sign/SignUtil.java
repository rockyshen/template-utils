package com.rockyshen.sign;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签名工具
 */
public class SignUtil {

    /**
     * 基于用户参数+secretKey，生成签名，提供给请求方法中
     * @param body
     * @param secretKey
     * @return
     */
    public static String genSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey;    // 用户参数 + secretKey，放入签名生成算法，生成唯一值
        String digestHex = md5.digestHex(content);
        return digestHex;
    }
}
