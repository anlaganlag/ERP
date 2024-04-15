package com.tadpole.cloud.externalSystem.modular.saihu.utils;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.tadpole.cloud.externalSystem.config.SaihuConfig;
import com.tadpole.cloud.externalSystem.api.saihu.model.result.SaiHuBaseResult;
import com.tadpole.cloud.externalSystem.modular.saihu.service.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: ty
 * @description: 赛狐请求信息处理工具类
 * @date: 2024/2/19
 */
@Slf4j
@Component
public class SaihuReqUtil {

    @Autowired
    private IAuthService authService;
    @Resource
    private SaihuConfig saihuConfig;
    @Resource
    private RestTemplate restTemplate;

    public static String URL = "url";
    public static String METHOD = "method";
    public static String ACCESS_TOKEN = "access_token";
    public static String CLIENT_ID = "client_id";
    public static String TIMESTAMP = "timestamp";
    public static String NONCE = "nonce";
    public static String SIGN = "sign";
    public static String METHOD_POST = "post";
    public static String HMAC_SHA256 = "HmacSHA256";
    public static String MARK_EQ = "=";
    public static String MARK_JOIN = "&";
    public static String MARK_QUES = "?";
    public static String EMPTY_BODY = "{}";

    /**
     * 赛狐业务post请求信息处理
     * @param t 实体参数
     * @param url 请求路径
     * @return
     */
    public <T> SaiHuBaseResult doPostReq(T t, String url) throws Exception {
        String token = authService.getToken();
        if (ObjectUtil.isEmpty(token)) {
            throw new Exception("请求赛狐接口获取Token失败");
        }
        Map<String, Object> params = new HashMap<>();
        Long currentTimeMillis = System.currentTimeMillis();
        Integer nonce = currentTimeMillis.intValue();
        //接口请求路径
        params.put(URL, url);
        params.put(METHOD, METHOD_POST);
        //通过接口获取的token信息
        params.put(ACCESS_TOKEN, token);
        //开发者id
        params.put(CLIENT_ID, saihuConfig.getClient_id());
        //请求时间戳 (ms)
        params.put(TIMESTAMP, currentTimeMillis);
        //请求随机整数
        params.put(NONCE, nonce);
        //参数排序
        String data = params.entrySet().stream().map(e -> e.getKey() + MARK_EQ + e.getValue()).sorted().collect(Collectors.joining(MARK_JOIN));
        //body参数不参与签名
        String sign = this.sign(saihuConfig.getClient_secret(), data);

        //请求头固定顺序参数
        String requestHeadParam = ACCESS_TOKEN+MARK_EQ+token+MARK_JOIN+CLIENT_ID+MARK_EQ+saihuConfig.getClient_id()+MARK_JOIN+TIMESTAMP+MARK_EQ+currentTimeMillis+MARK_JOIN+NONCE+MARK_EQ+nonce+MARK_JOIN+SIGN+MARK_EQ+sign;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        String jsonBody;
        if(t != null){
            jsonBody = JSON.toJSONString(t);
        } else {
            jsonBody = EMPTY_BODY;
        }
        HttpEntity httpEntity = new HttpEntity<>(jsonBody, headers);
        String reqUrl = saihuConfig.getBaseUrl() + url + MARK_QUES + requestHeadParam;
        try {
//            log.info("调用赛狐API接口POST请求参数：URL:{},Body参数{}", reqUrl, jsonBody);
            ResponseEntity<SaiHuBaseResult> restTemplateResult = restTemplate.exchange(reqUrl, HttpMethod.POST, httpEntity, SaiHuBaseResult.class);
//            log.info("调用赛狐API接口POST返回结果:{}", JSON.toJSONString(restTemplateResult));
            SaiHuBaseResult saiHuResult = restTemplateResult.getBody();
            return saiHuResult;
        } catch (Exception e) {
            log.error("请求赛狐接口异常[{}]，异常信息[{}]", reqUrl, e.getMessage());
            throw new RuntimeException("请求赛狐接口" + reqUrl + "异常，异常信息："+e.getMessage());
        }
    }

    /**
     * 签名
     * @param key 密钥
     * @param data 签名参数
     * @return
     * @throws Exception
     */
    private static String sign(String key, String data) throws Exception {
        Mac hmac = Mac.getInstance(HMAC_SHA256);
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
        hmac.init(secret_key);
        return new String(Hex.encodeHex(hmac.doFinal(data.getBytes(StandardCharsets.UTF_8))));
    }

}
