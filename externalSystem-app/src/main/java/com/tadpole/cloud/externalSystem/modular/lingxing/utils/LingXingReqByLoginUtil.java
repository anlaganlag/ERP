package com.tadpole.cloud.externalSystem.modular.lingxing.utils;

import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product.LinXingCategoryAddResp;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LinXingTokenResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author: lsy
 * @description:
 * 领星页面登录后获取token，模仿页面操作请求接口,
 * 不同业务操作请求头不一样需要自己定义
 * @date: 2022/12/2
 */
@Slf4j
@Component
public class LingXingReqByLoginUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    public LinXingCategoryAddResp addCategory(Integer parentCid, String title) {
        String url = "https://jcbsc.lingxing.com/api/product_extend/categoryAdd";

        HttpPost httpPost = this.getHttpPost(url);
        httpPost.addHeader(new BasicHeader("Host", "jcbsc.lingxing.com"));
        httpPost.addHeader(new BasicHeader("auth-token", this.loginToken()));

        HashMap<String, Object> bodyParm = new HashMap<>();
        bodyParm.put("parent_cid", parentCid);
        bodyParm.put("title", title);
        bodyParm.put("req_time_sequence", "/api/product_extend/categoryAdd$$1"); //目前是固定值

        String resultString = this.postReqByBodyJson(httpPost, bodyParm);

        if (StringUtils.isNotEmpty(resultString)) {
            LinXingCategoryAddResp linXingCategoryAddResp = JSON.parseObject(resultString, LinXingCategoryAddResp.class);
            return linXingCategoryAddResp;
        }
        return null;
    }


    /**
     * 领星erp网页登录时获取登录token
     */
    public String loginToken() {

        String loginToken = null;

        String LOGIN_TOKEN_KEY = "linxing_login_token_key";

        if (redisTemplate.hasKey(LOGIN_TOKEN_KEY)) {
            //token未过期，直接从缓存取
            loginToken = (String) redisTemplate.boundValueOps(LOGIN_TOKEN_KEY).get();
            return loginToken;
        }

        String url = "https://gw.lingxingerp.com/newadmin/api/passport/login";
        HttpPost httpPost = this.getHttpPost(url);
        httpPost.addHeader(new BasicHeader("Host", "gw.lingxingerp.com"));
        httpPost.addHeader(new BasicHeader("Referer", "https://jcbsc.lingxing.com/"));
        httpPost.addHeader(new BasicHeader("Origin", "https://jcbsc.lingxing.com"));

        HashMap<String, Object> bodyParm = new HashMap<>();
        bodyParm.put("account", "Majianfeng");
        bodyParm.put("pwd", "FNKRcjD8uFryeg9DbGIADQ==");
        bodyParm.put("verify_code", "");
        bodyParm.put("uuid", "f37588ff-16b8-40f9-a1a2-b16958516a84");
        bodyParm.put("auto_login", 1);
        bodyParm.put("sensorsAnonymousId", "1849eb0a468b2-0eddcbe3105d45-26021e51-2073600-1849eb0a469d32");

        String resultString = this.postReqByBodyJson(httpPost, bodyParm);

        if (StringUtils.isNotEmpty(resultString)) {
            LinXingTokenResult tokenResult = JSON.parseObject(resultString, LinXingTokenResult.class);
            loginToken = tokenResult.getToken();

            if (StringUtils.isNotEmpty(loginToken)) {
                redisTemplate.boundValueOps(LOGIN_TOKEN_KEY).set(loginToken, 431000, TimeUnit.SECONDS);
                log.info("请求领星登录获取token--成功[{}]", loginToken);
                return loginToken;
            }
        } else {
            log.info("请求领星登录获取token失败");
        }
        return loginToken;
    }


    /**
     * HttpPost共用请求头
     *
     * @param url
     * @return
     */
    private HttpPost getHttpPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(new BasicHeader("Accept-Encoding", "gzip, deflate, br"));
        httpPost.addHeader(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.9"));
        httpPost.addHeader(new BasicHeader("Content-Type", "application/json;charset=UTF-8"));
        httpPost.addHeader(new BasicHeader("Accept", "application/json, text/plain, */*"));
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");
        httpPost.addHeader(new BasicHeader("X-AK-Company-Id", "901167908147429376"));//目前是固定值
        return httpPost;
    }


    /**
     * post请求 json参数放在body
     *
     * @param httpPost
     * @param bodyParm
     * @return
     */
    private String postReqByBodyJson(HttpPost httpPost, HashMap<String, Object> bodyParm) {
        String stringResultEntity = null;
        StringEntity stringEntity = new StringEntity(JSON.toJSONString(bodyParm), Charset.forName("UTF-8"));
        stringEntity.setContentType(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
        stringEntity.setContentEncoding(Consts.UTF_8.name());
        httpPost.setEntity(stringEntity);


        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        CloseableHttpClient httpClient = (CloseableHttpClient) this.wrapClient(); //https 视情况使用

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                stringResultEntity = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                log.info("模仿页面操作请求领星接口[{}]--成功:{}", httpPost.getURI().toString(), stringResultEntity);
                return stringResultEntity;

            }
            log.info("模仿页面操作请求领星接口[{}]--失败:{}", httpPost.getURI().toString(), JSON.toJSONString(response));
        } catch (IOException e) {
            log.error("模仿页面操作请求领星接口[{}]--异常:{}", httpPost.getURI().toString(), JSON.toJSONString(e));
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (Exception e) {
                System.out.println("关闭资源异常：" + e.getMessage());
            }
            System.out.println("关闭资源成功");
        }
        return stringResultEntity;
    }

    /**
     * 绕过证书,生成https
     *
     * @return
     */
    public HttpClient wrapClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(
                    ctx, NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(ssf).build();
            return httpclient;
        } catch (Exception e) {
            return HttpClients.createDefault();
        }
    }

}
