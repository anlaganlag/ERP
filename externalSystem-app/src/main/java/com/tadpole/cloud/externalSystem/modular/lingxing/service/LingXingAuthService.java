package com.tadpole.cloud.externalSystem.modular.lingxing.service;

import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.auth.LingXingAuthRespData;

/**
 * @author: ty
 * @description: 领星授权Service接口类
 * @date: 2022/4/22
 */
public interface LingXingAuthService {

    /**
     * 获取access-token和refresh-token
     * @return
     */
    LingXingAuthRespData accessToken() throws Exception;

    /**
     * 刷新token（token续约，每个refreshToken只能用一次）
     * @return
     */
    LingXingAuthRespData refreshToken() throws Exception;

    public LingXingAuthRespData accessTokenByFormData() throws Exception;

    public LingXingAuthRespData refreshTokenByFormData() throws Exception;
}
