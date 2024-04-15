package com.tadpole.cloud.externalSystem.modular.lingxing.service.impl;

import com.tadpole.cloud.externalSystem.modular.lingxing.constants.LingXingUrlConstants;
import com.tadpole.cloud.externalSystem.modular.lingxing.core.Config;
import com.tadpole.cloud.externalSystem.modular.lingxing.core.HttpMethod;
import com.tadpole.cloud.externalSystem.modular.lingxing.core.HttpRequest;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.auth.LingXingAuthRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.okhttp.HttpExecutor;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingAuthService;
import com.tadpole.cloud.externalSystem.modular.lingxing.utils.LingXingTokenUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ty
 * @description: 领星授权service实现类
 * @date: 2022/4/22
 */
@Slf4j
@Service
public class LingXingAuthServiceImpl implements LingXingAuthService {

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
     * 领星appSecret
     */
    @Value("${lingxing.app-secret}")
    private String appSecret;

    /**
     * 领星接口请求超时时间
     */
    @Value("${lingxing.timeout}")
    private Integer timeout;

    @Autowired
    private LingXingTokenUtil lingXingTokenUtil;

    @Override
    public LingXingAuthRespData accessToken() throws Exception {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("appId", appId);
        queryParam.put("appSecret", appSecret);



        HttpRequest<LingXingAuthRespData> build = HttpRequest.builder(LingXingAuthRespData.class)
                .method(HttpMethod.POST)
                .endpoint(requestAddr)
                .path(LingXingUrlConstants.ACCESS_TOKEN)
                .queryParams(queryParam)
                .config(Config.DEFAULT.withConnectionTimeout(timeout).withReadTimeout(timeout))
                .build();

        return HttpExecutor.create().execute(build).readEntity(LingXingAuthRespData.class);
    }



    @Override
    public LingXingAuthRespData accessTokenByFormData() throws Exception {
        LingXingAuthRespData lingXingAuthRespData=null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost uploadFile = new HttpPost(requestAddr+LingXingUrlConstants.ACCESS_TOKEN);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("appId", appId, ContentType.TEXT_PLAIN);
            builder.addTextBody("appSecret", appSecret, ContentType.TEXT_PLAIN);
            HttpEntity multipart = builder.build();

            uploadFile.setEntity(multipart);
            String resultStr = httpClient.execute(uploadFile, new BasicResponseHandler());
            System.out.println(resultStr);
            lingXingAuthRespData = JSON.parseObject(resultStr, LingXingAuthRespData.class);

        } catch (Exception e) {
            log.error("方法accessTokenByFormData->>>>获取领星Token异常:[{}])", e.getMessage());
        }finally {
            httpClient.close();
        }
        return lingXingAuthRespData;
    }



    @Override
    public LingXingAuthRespData refreshTokenByFormData() throws Exception {

        LingXingAuthRespData lingXingAuthRespData=null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost uploadFile = new HttpPost(requestAddr+LingXingUrlConstants.REFRESH_TOKEN);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("appId", appId, ContentType.TEXT_PLAIN);
            builder.addTextBody("refreshToken", lingXingTokenUtil.getLingXingToken().getRefresh_token(), ContentType.TEXT_PLAIN);
            HttpEntity multipart = builder.build();


            uploadFile.setEntity(multipart);
            String resultStr = httpClient.execute(uploadFile, new BasicResponseHandler());
            System.out.println(resultStr);
            lingXingAuthRespData = JSON.parseObject(resultStr, LingXingAuthRespData.class);

        } catch (Exception e) {
            log.error("方法refreshTokenByFormData->>>>刷新领星Token异常:[{}])", e.getMessage());
        }finally {
            httpClient.close();
        }
        return lingXingAuthRespData;
    }


    @Override
    public LingXingAuthRespData refreshToken() throws Exception {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("appId", appId);
        queryParam.put("refreshToken", lingXingTokenUtil.getLingXingToken().getRefresh_token());
        HttpRequest<LingXingAuthRespData> build = HttpRequest.builder(LingXingAuthRespData.class)
                .method(HttpMethod.POST)
                .endpoint(requestAddr)
                .path(LingXingUrlConstants.REFRESH_TOKEN)
                .queryParams(queryParam)
                .config(Config.DEFAULT.withConnectionTimeout(timeout).withReadTimeout(timeout))
                .build();
        return HttpExecutor.create().execute(build).readEntity(LingXingAuthRespData.class);
    }
}
