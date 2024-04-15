package com.tadpole.cloud.externalSystem.modular.lingxing.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.tadpole.cloud.externalSystem.modular.lingxing.core.Config;
import com.tadpole.cloud.externalSystem.modular.lingxing.core.HttpMethod;
import com.tadpole.cloud.externalSystem.modular.lingxing.core.HttpRequest;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.okhttp.HttpExecutor;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ty
 * @description: 领星请求信息处理工具类
 * @date: 2022/4/24
 */
@Slf4j
@Component
public class LingXingReqInfoUtil {

    /**
     * 领星地址
     */
    @Value("${lingxing.addr}")
    private String requestAddr;

    /**
     * 领星appId
     */
    @Value("${lingxing.app-id}")
    private String appId;

    /**
     * 领星业务接口调用前缀
     */
    @Value("${lingxing.business-prefix-url}")
    private String businessPrefixUrl;

    /**
     * 领星接口请求超时时间
     */
    @Value("${lingxing.timeout}")
    private Integer timeout;

    @Autowired
    private LingXingTokenUtil lingXingTokenUtil;

    /**
     * 领星业务get请求信息处理
     * @param url 请求路径
     * @return
     */
    public LingXingBaseRespData doGetReq(String url) throws Exception {
        HttpRequest<Object> build = HttpRequest.builder(Object.class)
                .method(HttpMethod.GET)
                .endpoint(requestAddr)
                .path(businessPrefixUrl + url)
                .queryParams(buildQueryParam())
                .config(Config.DEFAULT.withConnectionTimeout(timeout).withReadTimeout(timeout))
                .build();
        return HttpExecutor.create().execute(build).readEntity(LingXingBaseRespData.class);
    }

    /**
     * 领星业务get请求信息处理
     * @param queryParam 请求参数
     * @param url 请求路径
     * @return
     */
    public LingXingBaseRespData doGetReq(Map<String, Object> queryParam, String url) throws Exception {
        queryParam.put("timestamp", System.currentTimeMillis()/1000 + "");
        queryParam.put("app_key", appId);
        queryParam.put("access_token", lingXingTokenUtil.getLingXingToken().getAccess_token());
        String sign = LingXingSignUtil.sign(queryParam, appId);
        queryParam.put("sign", sign);
        HttpRequest<Object> build = HttpRequest.builder(Object.class)
                .method(HttpMethod.GET)
                .endpoint(requestAddr)
                .path(businessPrefixUrl + url)
                .queryParams(queryParam)
                .config(Config.DEFAULT.withConnectionTimeout(timeout).withReadTimeout(timeout))
                .build();
        return HttpExecutor.create().execute(build).readEntity(LingXingBaseRespData.class);
    }

    /**
     * 领星业务post请求信息处理
     * @param url 请求路径
     * @return
     */
    public LingXingBaseRespData doPostReq(String url) throws Exception {
        HttpRequest<Object> build = HttpRequest.builder(Object.class)
                .method(HttpMethod.POST)
                .endpoint(requestAddr)
                .path(businessPrefixUrl + url)
                .queryParams(buildQueryParam())
                .config(Config.DEFAULT.withConnectionTimeout(timeout).withReadTimeout(timeout))
                .build();
        return HttpExecutor.create().execute(build).readEntity(LingXingBaseRespData.class);
    }

    /**
     * 领星业务post请求信息处理
     * @param t 实体参数
     * @param url 请求路径
     * @return
     */
    public <T> LingXingBaseRespData doPostReq(T t, String url) throws Exception {

        HttpExecutor executor = null;
        HttpRequest<Object> build = null;

        try{

        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("timestamp", System.currentTimeMillis()/1000 + "");
        queryParam.put("app_key", appId);
        queryParam.put("access_token", lingXingTokenUtil.getLingXingToken().getAccess_token());

        //基本参数和body参数都要参与签名
        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(queryParam);
        signMap.putAll(BeanUtil.beanToMap(t));

        String sign = LingXingSignUtil.sign(signMap, appId);
        queryParam.put("sign", sign);
         build = HttpRequest.builder(Object.class)
                .method(HttpMethod.POST)
                .endpoint(requestAddr)
                .path(businessPrefixUrl + url)
                .queryParams(queryParam)
                .json(JSON.toJSONString(t))
                .config(Config.DEFAULT.withConnectionTimeout(timeout).withReadTimeout(timeout))
                .build();
         executor = HttpExecutor.create();

        System.out.println("executor.hashCode>>>"+executor.hashCode()+">>>");
        System.out.println("executor.identityHashCode>>>"+System.identityHashCode(executor)+">>>");
        System.out.println("executor.hashCode.json>>>"+ JSONUtil.toJsonStr(executor) +">>>");

        LingXingBaseRespData data = executor.execute(build).readEntity(LingXingBaseRespData.class);
        return data;

        }catch (Exception e){
            log.error("post exception >>>> "  + e.getMessage());
            LingXingBaseRespData data = executor.execute(build).readEntity(LingXingBaseRespData.class);

            return data;
        }
    }

    /**
     * 领星业务post请求信息处理
     * @param t 实体参数
     * @param url 请求路径
     * @return
     */
    public <T> LingXingBaseRespData doPostReq1(T t, String url) throws Exception {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("timestamp", System.currentTimeMillis()/1000 + "");
        queryParam.put("app_key", appId);
        queryParam.put("access_token", lingXingTokenUtil.getLingXingToken().getAccess_token());

        //基本参数和body参数都要参与签名
        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(queryParam);
        signMap.putAll(BeanUtil.beanToMap(t));

        String sign = LingXingSignUtil.sign(signMap, appId);
        queryParam.put("sign", sign);
        HttpRequest<Object> build = HttpRequest.builder(Object.class)
                .method(HttpMethod.POST)
                .endpoint(requestAddr)
                .path(businessPrefixUrl + url)
                .queryParams(queryParam)
                .json(JSON.toJSONString(t))
                .config(Config.DEFAULT.withConnectionTimeout(timeout).withReadTimeout(timeout))
                .build();
        return HttpExecutor.create().execute(build).readEntity(LingXingBaseRespData.class);
    }

    /**
     * 领星queryParam参数
     * @return
     * @throws Exception
     */
    private Map<String, Object> buildQueryParam() throws Exception {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("timestamp", System.currentTimeMillis()/1000 + "");
        queryParam.put("app_key", appId);
        queryParam.put("access_token", lingXingTokenUtil.getLingXingToken().getAccess_token());
        String sign = LingXingSignUtil.sign(queryParam, appId);
        queryParam.put("sign", sign);
        return queryParam;
    }
}
