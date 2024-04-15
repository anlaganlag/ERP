package com.tadpole.cloud.externalSystem.modular.lingxing.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

/**
 * @author: ty
 * @description: 领星签名生成规则工具类
 * @date: 2022/4/24
 */
@Slf4j
@Component
public class LingXingSignUtil {

    public static String sign(Map<String, Object> params, String appSecret) throws UnsupportedEncodingException {
        // 参数排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder paramNameValue = new StringBuilder();
        for (String key : keys) {
            if(params.get(key) != null){
                String value = String.valueOf(params.get(key));
                if (StringUtils.isNoneBlank(key, value)) {
                    paramNameValue.append(key).append("=").append(value.trim()).append("&");
                }
            }
        }
        String paramValue = paramNameValue.toString();
        if (paramValue.endsWith("&")) {
            paramValue = paramValue.substring(0, paramValue.length() - 1);
        }
        // md5加密
        String md5Hex = DigestUtils.md5Hex(paramValue.getBytes(StandardCharsets.UTF_8)).toUpperCase();
        log.info("params append: {},md5Hex:{}", paramValue, md5Hex);
        return URLEncoder.encode(AesUtil.encryptEcb(md5Hex, appSecret),"UTF-8");
    }
}
