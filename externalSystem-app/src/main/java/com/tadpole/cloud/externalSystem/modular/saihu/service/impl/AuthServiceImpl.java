package com.tadpole.cloud.externalSystem.modular.saihu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.tadpole.cloud.externalSystem.api.saihu.model.result.SaiHuBaseResult;
import com.tadpole.cloud.externalSystem.config.SaihuConfig;
import com.tadpole.cloud.externalSystem.modular.saihu.constants.SaihuUrlConstants;
import com.tadpole.cloud.externalSystem.modular.saihu.service.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: ty
 * @description: 赛狐token服务实现类
 * @date: 2024/2/19
 */
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SaihuConfig saihuConfig;

    public static String TOKEN_KEY = "SAI_HU_TOKEN";

    @Override
    public String getToken() {
        if (redisTemplate.hasKey(TOKEN_KEY)) {
            //token未过期，直接从缓存取
            return (String) redisTemplate.boundValueOps(TOKEN_KEY).get();
        } else {
            return createToken();
        }
    }

    private String createToken() {
        Map<String, Object> tokenParamMap = new HashMap<>();
        tokenParamMap.put("client_id", saihuConfig.getClient_id());
        tokenParamMap.put("client_secret", saihuConfig.getClient_secret());
        tokenParamMap.put("grant_type", saihuConfig.getGrant_type());
        String resultStr = HttpUtil.get(saihuConfig.getBaseUrl() + SaihuUrlConstants.ACCESS_TOKEN, tokenParamMap);
        if (ObjectUtil.isNotEmpty(resultStr)) {
            SaiHuBaseResult saiHuResult = JSON.parseObject(resultStr, SaiHuBaseResult.class);
            if (SaiHuBaseResult.DEFAULT_SUCCESS_CODE.equals(saiHuResult.getCode()) && ObjectUtil.isNotNull(saiHuResult.getData())) {
                JSONObject data = JSONUtil.parseObj(saiHuResult.getData());
                String accessToken = data.get("access_token", String.class);
                Integer expiresIn = data.get("expires_in", Integer.class);
                redisTemplate.boundValueOps(TOKEN_KEY).set(accessToken,expiresIn, TimeUnit.MILLISECONDS);
                return accessToken;
            }
        }
        log.error("请求赛狐授权获取token信息异常，异常信息[{}]", resultStr);
        return null;
    }
}
