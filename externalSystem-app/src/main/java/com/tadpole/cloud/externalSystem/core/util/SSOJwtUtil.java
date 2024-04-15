package com.tadpole.cloud.externalSystem.core.util;

import com.alibaba.fastjson.JSON;
import com.asinking.uc.sso.bean.JwtSSOLoginDTO;
import com.asinking.uc.sso.exception.SSOLoginException;
import com.asinking.uc.sso.service.JWTLoginClient;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import javax.crypto.Cipher;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  SSO-JWT第三方系统代码示例
 * </p>
 */
public class SSOJwtUtil {

    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMl5dXIwbawEOpMY+x02TRMhRXl0Ng4fbF0xofgCRfPkeO9JKU/An4+MtSyXDQNEGh+Eb8e1M2zJ4M7zf3DZ2wBcXn3247xKK8xol8VW2ytuGm5yBvtQpuuwwMTuf4k5rmQj4cMW4Ob2hIumjI4TiwwpEV0CIN4HKsLPXmC2EMsdAgMBAAECgYA/51F0NZ4jqHe3vn2vx1BtF+mEW3LlydvCN4LrOjVb5YTiSO9ch3lUu8mfag3LkmdCxev6iSPVhrbSjXNHpSIMC/xKk/FMjKGXCCsMt8DTzIgOSS8V+7TSr/vxwOOEmQyYxrjdS748dXlAlQ8YslbUxIWO34qFLjpG+YkIb9JvNQJBAO9Ec9P9IQAuslcrhH4Lpph8PHaTdKp+R4xHbx2hbmTdgTg+cYKBTVGec3Caeocr8VgtfSZgPBJGEJi7RNcfXk8CQQDXkGh+dHYli7Sz/Cj4RQzXWAlmaw0Jgmc4eoucj5BGtM85ZtmSKZUXTM3iLidiD8euyOlEIgOL/15o1abNdPDTAkEA7oa5Sd6RZZMn60rQ3K9Ut7Myu6sopUcaoLgeB9YFLby8s4tcsZOhtvpVby4xdEvUX+mJWBacDEOZDAm1CRiWdQJAWpj+0ebwoOcOk3avYWjj9L2zdbAYUp7T8xDODIbqBE2Jqn5ngt6nIpvNC/qJ4tTu/67BGzmQdA5oB3eEG2XCsQJAQinkQqRb42t+yn7AbKm5vx9kFs0r3wlBYihYguM5HU/W2HYS5iyY1r2EmYpaJiHrKGXnND61w3N0k8GSMVD7uA==";
    private static final String URL_PARAM = "?authType=jwt&param=%s";

    /**
     * TODO 需要替换为客户访问领星ERP系统的域名，SAAS客户统一为 https://erp.lingxing.com,独立部署客户需要替换为自己的服务域名
     * 如果需要登录默认进入指定菜单，请携带具体路径
     * eg： 默认进入用户管理页面， https://erp.lingxing.com/erp/muser/userManage
     */
    private static final String DOMAIN = "https://jcbsc.lingxing.com";

    // TODO 需要替换为配置在ERP系统的验签密钥字段值 （菜单路径：设置/业务配置/全局/单点登录设置：验签密钥）
    private static final String SIGN_KEY = "071ce3abdb0e43b2819178e0702d9e1b";

    // TODO 需替换为配置在ERP系统的企业唯一标识值 （菜单路径：设置/业务配置/全局/单点登录设置：企业唯一标识）
    private static final String CLIENT_ID = "0F3A6FB10B2E910693F7477687014D61";

    // TODO 如果需要指定当前jwtToken的过期时间，请手动调整此项值
    public static final Long EXPIRE_TIME = 36000L;//过期时间，单位秒，默认一小时

    public static void main(String[] args) {
        // TODO 用户信息需替换为本系统的用户数据
        String redirectUrl = null;

        JWTLoginClient client = new JWTLoginClient();
        JwtSSOLoginDTO loginDTO = JwtSSOLoginDTO.builder()
                .domain(DOMAIN)
                .signKey(SIGN_KEY)
                .clientId(CLIENT_ID)
                .expireMilliseconds(EXPIRE_TIME)
                .uniqueKey("userId001")
                .mobile("13543292129")
                .email("2881537936@qq.com")
                .username("Dengyaliang")
                .realName("邓亚良")
                .build();
        redirectUrl = null;
        try {
            redirectUrl = client.getRedirectUrl(loginDTO);
        } catch (SSOLoginException e) {
            // TODO 异常处理
        }
        System.out.println(redirectUrl);
    }

    /**
     * 获取重定向到领星ERP系统的地址
     * @param uniqueKey
     * @param mobile
     * @param email
     * @param username
     * @param realname
     * @return
     */
    public static String getRedirectUrl(String uniqueKey, String mobile, String email, String username, String realname ){
        String jwtToken = createJWTToken(uniqueKey, mobile, email, username, realname);
        String jwtSSOLoginParam = encodeJwtSSOLoginParam(CLIENT_ID, jwtToken);
        return DOMAIN + String.format(URL_PARAM, jwtSSOLoginParam);
    }


    /**
     * 加密单点登录入参内容
     * @param clientId 企业唯一标识
     * @param jwtToken token信息
     * @return
     */
    private static String encodeJwtSSOLoginParam(String clientId, String jwtToken) {
        Map<String, Object> param = new HashMap<>();
        param.put("jwtToken", jwtToken);
        param.put("clientId", clientId);
        return encrypt(JSON.toJSONString(param), PRIVATE_KEY);
    }

    /**
     * 生成JWT-TOKEN
     * @return
     */
    private static String createJWTToken(String uniqueKey, String mobile, String email, String username, String realname) {
        Map<String, Object> header = new HashMap<>();
        header.put("type", "JWT");
        header.put("alg","HS256");
        Date exp = new Date(new Date().getTime() + EXPIRE_TIME * 1000);
        return JWT.create()
                .withHeader(header)
                .withSubject(uniqueKey)
                .withExpiresAt(exp)
                .withClaim("mobile", mobile)
                .withClaim("email", email)
                .withClaim("username", username)
                .withClaim("realname", realname)
                .sign(Algorithm.HMAC256(SIGN_KEY));
    }

    /**
     * RSA加密 - 私钥分段加密
     * @param content
     * @param privateKeyStr
     * @return
     */
    private static String encrypt(String content, String privateKeyStr) {
        try {
            // 获取私钥
            PrivateKey privateKey = getPrivateKey(privateKeyStr);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // URLEncoder编码解决中文乱码问题
            byte[] data = URLEncoder.encode(content, "UTF-8").getBytes("UTF-8");
            // 加密时超过117字节就报错。为此采用分段加密的办法来加密
            byte[] enBytes = null;
            for (int i = 0; i < data.length; i += 117) {
                // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
                byte[] doFinal = cipher.doFinal(subarray(data, i, i + 117));
                enBytes = addAll(enBytes, doFinal);
            }
            return Base64.getEncoder().encodeToString(enBytes).replaceAll("\\+", "-").replaceAll("/", "_").replaceAll("=", "");
        } catch(Exception e) {
            throw new RuntimeException("UC-RSA加密出错");
        }
    }

    /**
     * 将base64编码后的私钥字符串转成PrivateKey实例
     * @param privateKey 私钥
     * @return PrivateKey实例
     * @throws Exception 异常信息
     */
    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return new byte[0];
            } else {
                byte[] subarray = new byte[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    private static byte[] addAll(byte[] array1, byte... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            byte[] joinedArray = new byte[array1.length + array2.length];
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
            return joinedArray;
        }
    }

    private static byte[] clone(byte[] array) {
        return array == null ? null : (byte[])array.clone();
    }

}
