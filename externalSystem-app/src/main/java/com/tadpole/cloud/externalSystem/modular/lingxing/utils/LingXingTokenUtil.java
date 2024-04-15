package com.tadpole.cloud.externalSystem.modular.lingxing.utils;

import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.auth.LingXingAuthRespData;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.auth.LingXingToken;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.time.Duration;

/**
 * @author: ty
 * @description: 领星授权处理工具类
 * @date: 2022/4/22
 */
@Slf4j
@Component
public class LingXingTokenUtil {

    /**
     * 领星appId
     */
    @Value("${lingxing.app-id}")
    private String appId;

    /**
     * 领星token信息过期时间设定值
     */
    @Value("${lingxing.expires-in-value}")
    private Long expiresInValue;

    /**
     * 授权码对应的缓存前缀
     */
    @Value("${lingxing.lingxing-auth-token-prefix}")
    private String lingXingAuthTokenPrefix;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LingXingAuthService lingXingAuthService;

    /**
     * 获取redis获取领星token信息
     * @return
     */
    public LingXingToken getLingXingToken() throws Exception {
        if(redisTemplate.hasKey(lingXingAuthTokenPrefix + appId)){
            //token未过期，直接从缓存取
            LingXingToken lingXingToken = (LingXingToken) redisTemplate.boundValueOps(lingXingAuthTokenPrefix + appId).get();
            //当token过期时间小于设定值时，重新请求生成token信息
            if(expiresInValue.compareTo(redisTemplate.opsForValue().getOperations().getExpire(lingXingAuthTokenPrefix + appId)) > 0){
                lingXingToken = createLingXingToken();
            }
            return lingXingToken;
        }
        return createLingXingToken();
    }

    /**
     * 设置redis领星token信息：重新请求领星创建新的token信息
     * @return
     */
    public LingXingToken createLingXingToken() throws Exception {
//        LingXingAuthRespData authRespData = lingXingAuthService.accessToken();
        LingXingAuthRespData authRespData = lingXingAuthService.accessTokenByFormData();
        if(LingXingAuthRespData.DEFAULT_SUCCESS_CODE.equals(authRespData.getCode())){
            LingXingToken lingXingToken = authRespData.getData();

            //设置redis
            redisTemplate.boundValueOps(lingXingAuthTokenPrefix + appId)
                    .set(lingXingToken, Duration.ofSeconds(Long.parseLong(lingXingToken.getExpires_in())));
            return lingXingToken;
        }
        log.error("请求领星授权获取token信息异常，异常信息[{}]", authRespData);
        return null;
    }

    /**
     * 设置redis领星token信息：accessToken信息不变，刷新refreshToken和expiresIn
     * @return
     */
    public LingXingToken refreshLingXingToken() throws Exception {
//        LingXingAuthRespData authRespData = lingXingAuthService.refreshToken();
        LingXingAuthRespData authRespData = lingXingAuthService.refreshTokenByFormData();
        if(LingXingBaseRespData.DEFAULT_SUCCESS_CODE.equals(authRespData.getCode())){
            LingXingToken lingXingToken = authRespData.getData();

            //设置redis
            redisTemplate.boundValueOps(lingXingAuthTokenPrefix + appId)
                    .set(lingXingToken, Duration.ofSeconds(Long.parseLong(lingXingToken.getExpires_in())));
            return lingXingToken;
        }
        log.error("请求领星授权获取token信息异常，异常信息[{}]", authRespData);
        return null;
    }
}
