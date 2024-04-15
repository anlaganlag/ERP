package com.tadpole.cloud.externalSystem.modular.finebi.controller;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

import java.security.PublicKey;

/**
 * @author: ty
 * @description: 领星基础数据Controller类
 * @date: 2022/4/22
 */
@RestController
@ApiResource(name = "bi单点登录", path = "/finebi")
@Api(tags = "bi单点登录")
public class ssoController {

    private static final String BI_HTTP = "http://192.168.2.246/webroot/decision?ssoToken=";


    private static final String RSA_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl74oELJC1KdQ/soN12wWl7L2ZeDHPnqN\n" +
            "SHNNU7ndKZWMWHc+U/tXovgPnKFH0WH50e/kWtpWvtfTmIFB8NHSACOjUuZrDu3ZCZFB1LUYT8WF\n" +
            "ImWCnOCOoj9tQDTpcUpWXtO1jx6D8kUoCd20D9jSJbQznq0M8JW44Qyf9pyzoVv/utfceOJQ8gPM\n" +
            "tilUkeggm1R8gV8PMdMHNRPXzTcWmZ3+/dlEMMiO7537CRjaLGfKySAfin6nJZJKc2MOqbhLG5UQ\n" +
            "H/QdW/Ti8UkpM8d9WQVqWEmibitzJeebeTU7SaicOv3LZkheaDcfc89YZseCUVuO4G8xh054mdd+\n" +
            "JTgmlwIDAQAB";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static final String ALGORITHM_NAME = "RSA";


    @GetResource(name = "单点登录地址", path = "/getLoginUrl",requiredPermission = false, requiredLogin = true)
    @ApiOperation("单点登录地址")
    public ResponseData getLoginUrl() throws Exception {

        LoginUser loginUser = LoginContext.me().getLoginUser();

        return ResponseData.success(BI_HTTP + URLEncoder.encode(encrypt(loginUser.getAccount(),RSA_KEY),"utf-8"));
    }

    /**
     * RSA加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥
     */
    public static String encrypt(String data, String publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64(encryptedData));
    }

    /**
     * RSA加密，获取公钥
     * @param publicKey base64加密的公钥字符串
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        //使用decodeBase64进行破译编码，并返回一个byte字节数组
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes("utf-8"));
        //使用X509标准作为密钥规范管理的编码格式,按照 X509 标准对其进行编码的密钥。复制数组的内容，以防随后的修改。
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        //创建一个KeyFactory对象。
        //密钥工厂用于将 密钥 （ Key类型的不透明密码密钥）转换为 密钥规范 （底层密钥资料的透明表示）

        //返回一个KeyFactory对象，用于转换指定算法的公钥/私钥。
        //返回封装指定Provider对象的KeyFactorySpi实现的新KeyFactory对象。 请注意，指定的Provider对象不必在提供程序列表中注册。
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
        //根据提供的密钥规范（密钥材料）生成公钥对象。
        return keyFactory.generatePublic(keySpec);
    }

}
